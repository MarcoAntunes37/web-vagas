package com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth;

public interface KeycloakAuthClient {
    String getAccessToken() throws Exception;
}
