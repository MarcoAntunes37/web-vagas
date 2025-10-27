package com.webvagas.api.admin_api.application.abstraction.base_message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobsRequest;
import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobsResponse;
import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobResponse;
import com.webvagas.api.admin_api.domain.entity.jobs_user.dto.JobsUserResponse;
import com.webvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerRequest;
import com.webvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerResponse;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.webvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.webvagas.api.admin_api.infrastructure.integration.urlshortener.UrlShortenerClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.JobsUserClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.UserPreferencesClient;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseMessageService {
    protected final JSearchClient jsearchClient;
    protected final JobsUserClient jobsUserClient;
    protected final UserPreferencesClient userPreferencesClient;
    protected final UrlShortenerClient urlShortenerClient;
    protected final String accountSid;
    protected final String authToken;
    protected final String twilioNumber;
    protected final String jobListStringBase;
    protected final String messageServiceId;
    protected final String templateWithJobsId;
    protected final String templateWithoutJobsId;

    protected BaseMessageService(
            JSearchClient jsearchClient,
            JobsUserClient jobsUserClient,
            UserPreferencesClient userPreferencesClient,
            UrlShortenerClient urlShortenerClient,
            String accountSid,
            String authToken,
            String twilioNumber,
            String jobListStringBase,
            String messageServiceId,
            String templateWithJobsId,
            String templateWithoutJobsId) {
        this.jsearchClient = jsearchClient;
        this.jobsUserClient = jobsUserClient;
        this.urlShortenerClient = urlShortenerClient;
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.userPreferencesClient = userPreferencesClient;
        this.twilioNumber = twilioNumber;
        this.jobListStringBase = jobListStringBase;
        this.messageServiceId = messageServiceId;
        this.templateWithJobsId = templateWithJobsId;
        this.templateWithoutJobsId = templateWithoutJobsId;
    }

    protected List<GetJobResponse> fetchJobs(
            UserPreferencesGetResponse preferences,
            Integer page) {
        GetJobsRequest request = requestIsSimple(preferences)
                ? GetJobsRequest.simple(preferences.keywords(), page)
                : GetJobsRequest.complete(page, preferences);

        ResponseEntity<GetJobsResponse> response = jsearchClient.getJobs(request);

        return Optional.ofNullable(response.getBody())
                .map(GetJobsResponse::data)
                .orElse(new ArrayList<>());
    }

    protected List<GetJobResponse> filterNewJobsForUser(
            String userId,
            List<GetJobResponse> fetchedJobs,
            int jobsNeededPerMessage,
            String token) throws JsonProcessingException {
        List<String> jobIds = extractJobsIds(fetchedJobs);

        List<CreateUrlShortenerRequest> jobApplyLinks = extractJobsUrl(fetchedJobs);

        List<JobsUserResponse> receivedStatus = jobsUserClient
                .getJobsUser(userId, jobIds, token)
                .getBody();

        if (receivedStatus == null) {
            return Collections.emptyList();
        }

        List<CreateUrlShortenerResponse> shortUrlsList = urlShortenerClient
                .createShortUrl(jobApplyLinks)
                .getBody();

        if (shortUrlsList == null || shortUrlsList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, String> shortUrlMap = shortUrlsList.stream()
                .collect(Collectors.toMap(
                        CreateUrlShortenerResponse::originalUrl,
                        CreateUrlShortenerResponse::code,
                        (a, b) -> a));

        List<GetJobResponse> newJobs = new ArrayList<>();

        for (int i = 0; i < jobIds.size() && newJobs.size() < jobsNeededPerMessage; i++) {
            if (!receivedStatus.get(i).exists()) {
                GetJobResponse job = fetchedJobs.get(i);
                String shortenedUrl = shortUrlMap.getOrDefault(job.jobApplyLink(), job.jobApplyLink());

                newJobs.add(new GetJobResponse(
                        job.id(),
                        job.employerName(),
                        job.jobTitle(),
                        job.jobPostedDateTime(),
                        shortenedUrl));
            }
        }

        return newJobs;
    }

    protected List<String> extractJobsIds(List<GetJobResponse> jobs) {
        return jobs.stream()
                .map(GetJobResponse::id)
                .filter(id -> id != null && !id.isBlank())
                .toList();
    }

    protected List<CreateUrlShortenerRequest> extractJobsUrl(List<GetJobResponse> jobs) {
        return jobs.stream()
                .map(GetJobResponse::jobApplyLink)
                .filter(link -> link != null && !link.isBlank())
                .map(link -> new CreateUrlShortenerRequest(link))
                .toList();
    }

    protected List<String> buildJobListString(GetUserByRoleResponse user, List<GetJobResponse> jobsToSend,
            String jobListStringBase) {
        return jobsToSend
                .stream()
                .map(job -> job.employerName() + " - " + job.jobTitle() + " - " + jobListStringBase
                        + job.jobApplyLink())
                .toList();
    }

    protected Message createMessageWithJobs(String to, List<String> jobsListString, String name) throws Exception {
        Twilio.init(accountSid, authToken);

        Integer paramsCount = 2;

        Map<String, String> contentVariables = new HashMap<>();

        contentVariables.put("1", name);
        for (int i = 0; i < jobsListString.size(); i++) {
            contentVariables.put(String.valueOf(paramsCount), jobsListString.get(i));
            paramsCount++;
        }

        String jsonContentVariables = mapToJson(contentVariables);

        return Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + twilioNumber),
                "")
                .setMessagingServiceSid(messageServiceId)
                .setContentSid(templateWithJobsId)
                .setContentVariables(jsonContentVariables)
                .create();
    }

    protected Message createMessageWithoutJobs(String to, String name)
            throws Exception {
        Twilio.init(accountSid, authToken);
        Map<String, String> contentVariables = new HashMap<>();
        contentVariables.put("1", name);

        String jsonContentVariables = mapToJson(contentVariables);

        return Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + twilioNumber),
                "")
                .setMessagingServiceSid(messageServiceId)
                .setContentSid(templateWithoutJobsId)
                .setContentVariables(jsonContentVariables)
                .create();
    }

    protected boolean requestIsSimple(UserPreferencesGetResponse preferences) {
        return preferences.country() == null
                && preferences.employmentTypes() == null
                && preferences.excludeJobPublishers() == null
                && preferences.remoteWork() == null;
    }

    protected void processUserData(GetUserByRoleResponse user, int jobsNeededPerMessage, String token)
            throws Exception {
        int page = 1;

        List<GetJobResponse> jobsToSend = new ArrayList<>();

        String userId = user.id();

        String userPhone = user.attributes().phone()[0];

        Optional<UserPreferencesGetResponse> preferences = userPreferencesClient.findUserPreferences(userId, token);

        if (preferences.isEmpty()) {
            System.out.println("Skipping user: no preferences found");
            return;
        }

        UserPreferencesGetResponse preferencesValue = preferences.get();

        if (preferencesValue.keywords() == null || preferencesValue.keywords().isEmpty()) {
            System.out.println("Skipping user: no keywords found");
            return;
        }

        while (jobsNeededPerMessage > 0) {
            List<GetJobResponse> fetchedJobs = fetchJobs(preferencesValue, page);

            if (fetchedJobs.size() == 0)
                break;

            List<GetJobResponse> newJobs = filterNewJobsForUser(userId, fetchedJobs, jobsNeededPerMessage, token);

            for (GetJobResponse job : newJobs) {
                jobsToSend.add(job);
                jobsNeededPerMessage--;
                if (jobsNeededPerMessage == 0)
                    break;
            }

            if (fetchedJobs.size() < jobsNeededPerMessage)
                break;

            page++;
        }

        if (jobsToSend.size() > 0) {
            List<String> messageToUser = buildJobListString(user, jobsToSend, jobListStringBase);

            List<String> jobsIds = extractJobsIds(jobsToSend);

            try {
                Message message = createMessageWithJobs(userPhone, messageToUser, user.firstName());
                log.info(message.toString());
                String response = jobsUserClient.createJobUser(userId, jobsIds, token);
                log.info("Response: {}", response);
            } catch (Exception e) {
                log.error("Error sending message to user: {}", e);
            }
        }

        if (jobsToSend.size() == 0) {
            try {
                Message message = createMessageWithoutJobs(userPhone, user.firstName());
                log.info(message.toString());
            } catch (Exception e) {
                log.error("Error sending message to user: {}", e);
            }
        }
    }

    protected String mapToJson(Map<String, String> map) {
        return new JSONObject(map).toString();
    }
}