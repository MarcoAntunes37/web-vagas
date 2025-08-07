package com.flashvagas.api.admin_api.infrastructure.integration.flashvagas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPreferencesClient {
    private final RestTemplate restTemplate;

    @Value("${user-preferences.url}")
    private String url;

    public UserPreferencesGetResponse findUserPreferences(String userId, String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserPreferencesGetResponse> userPreferences = restTemplate.exchange(
                url + userId,
                HttpMethod.GET,
                entity,
                UserPreferencesGetResponse.class);

        return userPreferences.getBody();
    }
}