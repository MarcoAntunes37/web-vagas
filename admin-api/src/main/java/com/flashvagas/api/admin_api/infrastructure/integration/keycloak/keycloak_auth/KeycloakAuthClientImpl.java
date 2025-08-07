package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeycloakAuthClientImpl implements KeycloakAuthClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${keycloak.client_id}")
    private String clientId;

    @Value("${keycloak.username}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.grant_type}")
    private String grantType;

    @Value("${keycloak.token_url}")
    private String tokenUrl;

    public String getAccessToken() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("username", username);
        params.add("password", password);
        params.add("grant_type", grantType);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, params, String.class);

        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        return jsonNode.get("access_token").asText();
    }
}