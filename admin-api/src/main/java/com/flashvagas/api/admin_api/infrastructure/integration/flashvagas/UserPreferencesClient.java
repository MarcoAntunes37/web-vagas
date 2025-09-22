package com.flashvagas.api.admin_api.infrastructure.integration.flashvagas;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPreferencesClient {
    private final RestTemplate restTemplate;

    @Value("${user-preferences.url}")
    private String url;

    public Optional<UserPreferencesGetResponse> findUserPreferences(String userId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.set("Content-Type", "application/json");

            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<UserPreferencesGetResponse> userPreferences = restTemplate.exchange(
                    url + userId,
                    HttpMethod.GET,
                    entity,
                    UserPreferencesGetResponse.class);

            return Optional.of(userPreferences.getBody());
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        }
    }
}