package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;

@Component
public class KeycloakClientUtils {
    private static String host;

    private static String realm;

    @Value("${keycloak.host}")
    private String hostProp;

    @Value("${keycloak.realm}")
    private String realmProp;

    public static HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static String buildUrlGetByEmail(String email) {
        return UriComponentsBuilder.fromUriString(host)
                .pathSegment("admin", "realms", realm, "users")
                .queryParam("email", email)
                .toUriString();
    }

    public static String buidlUrlGetUserRoles(String userId) {
        return UriComponentsBuilder.fromUriString(host)
                .pathSegment("admin", "realms", realm, "users")
                .queryParam("userId", userId)
                .toUriString();
    }

    public static String buildUrlGetByRole(String role) {
        return UriComponentsBuilder.fromUriString(host)
                .pathSegment("admin", "realms", realm, "roles", role, "users")
                .toUriString();
    }

    public static String buildUrlUpdateRole(String userId) {
        return UriComponentsBuilder.fromUriString(host)
                .pathSegment("admin", "realms", realm, "users", userId, "role-mappings", "realm")
                .toUriString();
    }

    @PostConstruct
    public void init() {
        host = hostProp;
        realm = realmProp;
    }
}
