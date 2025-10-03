package com.webvagas.api.admin_api.application.service.plan_message;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.webvagas.api.admin_api.application.abstraction.base_message.BaseMessageService;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.webvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth.KeycloakAuthClientImpl;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user.KeycloakUserClientImpl;
import com.webvagas.api.admin_api.infrastructure.integration.urlshortener.UrlShortenerClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.JobsUserClient;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.UserPreferencesClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("StartMessageServiceImpl")
@Slf4j
public class StartMessageServiceImpl extends BaseMessageService implements PlanMessageService {
    private final KeycloakAuthClientImpl kcAuthClient;
    private final KeycloakUserClientImpl kcUserClient;
    @SuppressWarnings("unused")
    private final UserPreferencesClient userPreferencesClient;
    @SuppressWarnings("unused")
    private final JobsUserClient jobsUserClient;
    @SuppressWarnings("unused")
    private final JSearchClient jsearchClient;
    @SuppressWarnings("unused")
    private final UrlShortenerClient urlShortenerClient;
    @Value("${plans.start.jobs.quantity.per-message}")
    private Integer startJobsQuantityPerMessage;
    @Value("${plans.start.jobs.quantity.per-day}")
    private Integer startJobsQuantityPerDay;
    @Value("${job-list-string.base}")
    private String jobListStringBase;

    public StartMessageServiceImpl(
            KeycloakAuthClientImpl kcAuthClient,
            KeycloakUserClientImpl kcUserClient,
            JobsUserClient jobsUserClient,
            JSearchClient jsearchClient,
            UserPreferencesClient userPreferencesClient,
            UrlShortenerClient urlShortenerClient,
            @Value("${twilio.accountSID}") String accountSid,
            @Value("${twilio.authToken}") String authToken,
            @Value("${twilio.phoneNumber}") String twilioNumber,
            @Value("${job-list-string.base}") String jobListStringBase,
            @Value("${twilio.messageService}") String messageServiceId,
            @Value("${twilio.templateWithJobsId}") String templateWithJobsId,
            @Value("${twilio.templateWithoutJobsId}") String templateWithoutJobsId) {
        super(jsearchClient, jobsUserClient, userPreferencesClient, urlShortenerClient, accountSid, authToken,
                twilioNumber, jobListStringBase, messageServiceId, templateWithJobsId, templateWithoutJobsId);
        this.kcAuthClient = kcAuthClient;
        this.jsearchClient = jsearchClient;
        this.kcUserClient = kcUserClient;
        this.jobsUserClient = jobsUserClient;
        this.userPreferencesClient = userPreferencesClient;
        this.urlShortenerClient = urlShortenerClient;
    }

    @Override
    public void sendMessages() throws Exception {
        String token = kcAuthClient.getAccessToken();

        log.info("token: {}", token);

        List<GetUserByRoleResponse> startUsers = kcUserClient
                .getUsersByRole("plan-start", token);

        if (startUsers.isEmpty()) {
            log.warn("Nenhum usuário com role plan-start encontrado");
        }

        for (GetUserByRoleResponse user : startUsers) {
            this.processUserData(user, startJobsQuantityPerMessage, token);
        }
    }
}