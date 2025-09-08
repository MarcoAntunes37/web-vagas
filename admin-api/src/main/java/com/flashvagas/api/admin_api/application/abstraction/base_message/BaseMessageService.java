package com.flashvagas.api.admin_api.application.abstraction.base_message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsRequest;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsResponse;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobResponse;
import com.flashvagas.api.admin_api.domain.entity.jobs_user.dto.JobsUserResponse;
import com.flashvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerRequest;
import com.flashvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerResponse;
import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.flashvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.JobsUserClient;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.UserPreferencesClient;
import com.flashvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.flashvagas.api.admin_api.infrastructure.integration.urlshortener.UrlShortenerClient;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
    @Autowired
    protected ObjectMapper mapper;

    protected BaseMessageService(
            JSearchClient jsearchClient,
            JobsUserClient jobsUserClient,
            UserPreferencesClient userPreferencesClient,
            UrlShortenerClient urlShortenerClient,
            String accountSid,
            String authToken,
            String twilioNumber) {
        this.jsearchClient = jsearchClient;
        this.jobsUserClient = jobsUserClient;
        this.urlShortenerClient = urlShortenerClient;
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.userPreferencesClient = userPreferencesClient;
        this.twilioNumber = twilioNumber;
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
            int jobsNeeded,
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

        for (int i = 0; i < jobIds.size() && newJobs.size() < jobsNeeded; i++) {
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

    protected String buildJobListString(GetUserByRoleResponse user, List<GetJobResponse> jobsToSend) {
        StringBuilder jobListString = new StringBuilder();

        for (GetJobResponse job : jobsToSend) {
            jobListString
                    .append("*")
                    .append(job.employerName())
                    .append("*")
                    .append("\n\n")
                    .append("_")
                    .append(job.jobTitle())
                    .append("_")
                    .append("\n\n")
                    .append(job.jobApplyLink())
                    .append("\n\n");
        }

        return jobListString.toString();
    }

    protected Message createMessageWithJobs(String to, String jobsListString, String name) throws Exception {
        Twilio.init(accountSid, authToken);

        Map<String, String> contentVariables = new HashMap<>();
        contentVariables.put("1", name);
        contentVariables.put("2", jobsListString);

        String jsonContentVariables = mapper.writeValueAsString(contentVariables);

        return Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + twilioNumber),
                "")
                .setContentSid("HX949f3c1f7cd3a6a4d223c342d3a01e61")
                .setContentVariables(jsonContentVariables)
                .create();
    }

    protected Message createMessageWithoutJobs(String to, String name) throws Exception {
        Twilio.init(accountSid, authToken);
        Map<String, String> contentVariables = new HashMap<>();
        contentVariables.put("1", name);

        String jsonContentVariables = mapper.writeValueAsString(contentVariables);

        return Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + twilioNumber),
                "")
                .setContentSid("HX81cb2a2e180feb216e078310f365bfdc")
                .setContentVariables(jsonContentVariables)
                .create();
    }

    protected boolean requestIsSimple(UserPreferencesGetResponse preferences) {
        return preferences.country() == null
                && preferences.employmentTypes() == null
                && preferences.excludeJobPublishers() == null
                && preferences.remoteWork() == null;
    }

    protected void processUserData(GetUserByRoleResponse user, int jobsNeeded, String token) throws Exception {
        int page = 1;

        List<GetJobResponse> jobsToSend = new ArrayList<>();

        String userId = user.id();

        String userPhone = user.attributes().phone()[0];

        UserPreferencesGetResponse preferences = userPreferencesClient.findUserPreferences(userId, token);

        if (preferences.keywords() == null || preferences.keywords().isEmpty()) {
            System.out.println("Skipping preferences: no keywords found");
            return;
        }

        while (jobsNeeded > 0) {
            List<GetJobResponse> fetchedJobs = fetchJobs(preferences, page);

            if (fetchedJobs.size() == 0)
                break;

            List<GetJobResponse> newJobs = filterNewJobsForUser(userId, fetchedJobs, jobsNeeded, token);

            for (GetJobResponse job : newJobs) {
                jobsToSend.add(job);
                jobsNeeded--;
                if (jobsNeeded == 0)
                    break;
            }

            if (fetchedJobs.size() < 5)
                break;

            page++;
        }

        if (jobsToSend.size() > 0) {
            String messageToUser = buildJobListString(user, jobsToSend);

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
}