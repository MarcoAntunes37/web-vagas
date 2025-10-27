package com.webvagas.api.admin_api.application.service.keycloak.query.factory;

import org.springframework.stereotype.Component;

import com.webvagas.api.admin_api.application.service.keycloak.query.ExecuteWithUserQuery;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user.KeycloakUserClient;

@Component
public class ExecuteWithUserQueryExecutor {

    private final KeycloakUserClient kcUserClient;

    public ExecuteWithUserQueryExecutor(KeycloakUserClient kcUserClient) {
        this.kcUserClient = kcUserClient;
    }

    public void execute(ExecuteWithUserQuery query, String token) {
        GetUserByEmailResponse user = kcUserClient.getUserByEmail(query.email(), token);
        
        query.action().accept(user, token);
    }
}