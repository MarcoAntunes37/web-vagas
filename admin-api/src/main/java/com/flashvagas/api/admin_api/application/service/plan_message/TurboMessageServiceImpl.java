package com.flashvagas.api.admin_api.application.service.plan_message;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flashvagas.api.admin_api.application.abstraction.base_message.BaseMessageService;
import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.JobsUserClient;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.UserPreferencesClient;
import com.flashvagas.api.admin_api.infrastructure.integration.jsearch.JSearchClient;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth.KeycloakAuthClientImpl;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user.KeycloakUserClientImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("TurboMessageServiceImpl")
@Slf4j
public class TurboMessageServiceImpl extends BaseMessageService implements PlanMessageService {
    private final KeycloakAuthClientImpl kcAuthClient;
    private final KeycloakUserClientImpl kcUserClient;
    @SuppressWarnings("unused")
    private final UserPreferencesClient userPreferencesClient;
    @SuppressWarnings("unused")
    private final JobsUserClient jobsUserClient;
    @SuppressWarnings("unused")
    private final JSearchClient jsearchClient;
    @Value("${plans.turbo.jobs.quantity}")
    private Integer turboJobsQuantity;

    public TurboMessageServiceImpl(
            KeycloakAuthClientImpl kcAuthClient,
            KeycloakUserClientImpl kcUserClient,
            JobsUserClient jobsUserClient,
            JSearchClient jsearchClient,
            UserPreferencesClient userPreferencesClient,
            @Value("${twilio.accountSID}") String accountSid,
            @Value("${twilio.authToken}") String authToken,
            @Value("${twilio.phoneNumber}") String twilioNumber) {
        super(jsearchClient, jobsUserClient, userPreferencesClient, accountSid, authToken, twilioNumber);
        this.kcAuthClient = kcAuthClient;
        this.jsearchClient = jsearchClient;
        this.kcUserClient = kcUserClient;
        this.jobsUserClient = jobsUserClient;
        this.userPreferencesClient = userPreferencesClient;
    }

    @Override
    public void sendMessages() throws Exception {
        String token = kcAuthClient.getAccessToken();

        log.info("token: {}", token);

        List<GetUserByRoleResponse> turboUsers = kcUserClient.getUsersByRole("plan-turbo", token);

        if (turboUsers.isEmpty()) {
            log.warn("Nenhum usuário com role plan-turbo encontrado");
        }

        for (GetUserByRoleResponse user : turboUsers) {
            this.processUserData(user, turboJobsQuantity, token);
        }
    }
}
