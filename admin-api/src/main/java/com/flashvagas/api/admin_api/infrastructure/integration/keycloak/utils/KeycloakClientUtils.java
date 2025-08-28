package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeycloakClientUtils {
    private static String host;

    private static String realm;

    private static String profile;

    @Value("${keycloak.host}")
    private String hostProp;

    @Value("${keycloak.realm}")
    private String realmProp;

    @Value("${spring.profiles.active}")
    private String profileProp;

    public static HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static String buildUrlGetByEmail(String email) {
        String formattedHost = formatHost();       
        return UriComponentsBuilder
                .fromUriString(formattedHost)
                .pathSegment("admin", "realms", realm, "users")
                .queryParam("email", email)
                .toUriString();
    }

    public static String buidlUrlGetUserRoles(String userId) {
        String formattedHost = formatHost();
        return UriComponentsBuilder.fromUriString(formattedHost)
                .pathSegment("admin", "realms", realm, "users")
                .queryParam("userId", userId)
                .toUriString();
    }

    public static String buildUrlGetByRole(String role) {
        String formattedHost = formatHost();
        return UriComponentsBuilder.fromUriString(formattedHost)
                .pathSegment("admin", "realms", realm, "roles", role, "users")
                .toUriString();
    }

    public static String buildUrlUpdateRole(String userId) {
        String formattedHost = formatHost();
        return UriComponentsBuilder.fromUriString(formattedHost)
                .pathSegment("admin", "realms", realm, "users", userId, "role-mappings", "realm")
                .toUriString();
    }

    @PostConstruct
    public void init() {
        host = hostProp;
        realm = realmProp;
        profile = profileProp;
    }

    private static String formatHost() {
        String formattedHost = "";
        switch (profile) {
            case "dev":
                formattedHost = "http://" + host;
                break;
            case "prod":
                formattedHost = "https://" + host;
            default:
                break;
        }

        return formattedHost;
    }
}
