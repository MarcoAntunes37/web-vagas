package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.user.dto.AuthResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeycloakAuthClientImpl implements KeycloakAuthClient {
    private final RestTemplate restTemplate;
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
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("username", username);
        params.add("password", password);
        params.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                AuthResponse.class);

        return Optional.ofNullable(response.getBody())
                .map(AuthResponse::accessToken)
                .orElseThrow(() -> new Exception("Can't extract access token"));
    }
}