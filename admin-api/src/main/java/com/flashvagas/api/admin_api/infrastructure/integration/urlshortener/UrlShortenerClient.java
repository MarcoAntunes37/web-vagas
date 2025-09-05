package com.flashvagas.api.admin_api.infrastructure.integration.urlshortener;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerRequest;
import com.flashvagas.api.admin_api.domain.entity.url_shortener.dto.CreateUrlShortenerResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UrlShortenerClient {
    private final RestTemplate restTemplate;

    @Value("${urlshortener.url}")
    private String baseUrl;

    public ResponseEntity<List<CreateUrlShortenerResponse>> createShortUrl(List<CreateUrlShortenerRequest> request) {
        if (baseUrl == null) {
            throw new RuntimeException("urlshortener.url is not set");
        }

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        HttpEntity<List<CreateUrlShortenerRequest>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<List<CreateUrlShortenerResponse>> response = restTemplate.exchange(
                baseUrl + "/shorten/batch",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<CreateUrlShortenerResponse>>() {
                });

        return response;
    }
}