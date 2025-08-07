package com.flashvagas.api.admin_api.application.abstraction.base_message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsRequest;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsResponse;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobResponse;
import com.flashvagas.api.admin_api.domain.entity.jobs_user.dto.JobsUserResponse;
import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.flashvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.JobsUserClient;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.UserPreferencesClient;
import com.flashvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseMessageService {
    protected final JSearchClient jsearchClient;
    protected final JobsUserClient jobsUserClient;
    protected final UserPreferencesClient userPreferencesClient;
    protected final String accountSid;
    protected final String authToken;

    protected BaseMessageService(
            JSearchClient jsearchClient,
            JobsUserClient jobsUserClient,
            UserPreferencesClient userPreferencesClient,
            String accountSid,
            String authToken) {
        this.jsearchClient = jsearchClient;
        this.jobsUserClient = jobsUserClient;
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.userPreferencesClient = userPreferencesClient;
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

    protected List<GetJobResponse> filterNewJobsForUser(String userId, List<GetJobResponse> fetchedJobs,
            int jobsNeeded,
            String token)
            throws JsonProcessingException {
        List<String> jobIds = extractJobsIds(fetchedJobs);

        ResponseEntity<List<JobsUserResponse>> jobsReceivedResponse = jobsUserClient
                .getJobsUser(userId, jobIds, token);

        List<JobsUserResponse> receivedStatus = jobsReceivedResponse.getBody();

        if (receivedStatus == null)
            return new ArrayList<>();

        List<GetJobResponse> newJobs = new ArrayList<>();

        for (int i = 0; i < jobIds.size(); i++) {
            if (!receivedStatus.get(i).exists() && newJobs.size() < jobsNeeded) {
                newJobs.add(fetchedJobs.get(i));
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

    protected String buildMessage(GetUserByRoleResponse user, List<GetJobResponse> jobsToSend) {
        StringBuilder greetingsMessage = new StringBuilder();

        greetingsMessage.append("Olá ")
                .append(user.firstName())
                .append(", ")
                .append("temos novas vagas para você!\n\n");

        for (GetJobResponse job : jobsToSend) {
            greetingsMessage
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

        greetingsMessage.append("\n\nGostaríamos muito de saber o que você achou do nosso serviço!")
                .append("\nSua opinião é essencial para que possamos continuar melhorando.")
                .append("\n\nSe puder dedicar alguns minutinhos, responda ao nosso formulário: ")
                .append("https://form.jotform.com/252053164981659");

        return greetingsMessage.toString();
    }

    protected Message createMessage(String to, String message) throws Exception {
        Twilio.init(accountSid, authToken);
        return Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:+14155238886"),
                message)
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
            throw new IllegalArgumentException("At least keywords are required");
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
            String messageToUser = buildMessage(user, jobsToSend);

            List<String> jobsIds = extractJobsIds(jobsToSend);

            try {
                Message message = createMessage(userPhone, messageToUser);
                log.info(message.toString());
                String response = jobsUserClient.createJobUser(userId, jobsIds, token);
                log.info("Response: {}", response);
            } catch (Exception e) {
                log.error("Error sending message to user: {}", e);
            }
        }
    }
}
