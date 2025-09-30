package com.webvagas.api.admin_api.infrastructure.integration.webvagas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.webvagas.api.admin_api.domain.entity.jobs_user.dto.CreateJobUserRequest;
import com.webvagas.api.admin_api.domain.entity.jobs_user.dto.JobsUserResponse;
import com.webvagas.api.admin_api.infrastructure.integration.webvagas.utils.WebVagasUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobsUserClient {
    private final RestTemplate restTemplate;

    @Autowired
    private WebVagasUtil webVagasUtil;

    public ResponseEntity<List<JobsUserResponse>> getJobsUser(String userId, List<String> jobsIds, String token) {
        ResponseEntity<List<JobsUserResponse>> response = null;

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        headers.set("Authorization", "Bearer " + token);

        HttpEntity<List<JobsUserResponse>> entity = new HttpEntity<>(headers);

        String fullUrl = webVagasUtil.buildUrlGetJobsUser(userId, jobsIds);

        response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<JobsUserResponse>>() {
                });

        return response;
    }

    public Integer countJobsUser(String userId, String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String fullUrl = webVagasUtil.buildUrlCountJobsUserByInterval(userId);

        ResponseEntity<Integer> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                entity,
                Integer.class);

        return response.getBody();
    }

    public String createJobUser(String userId, List<String> jobsIds, String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        headers.set("Authorization", "Bearer " + token);

        CreateJobUserRequest request = new CreateJobUserRequest(userId, jobsIds);

        HttpEntity<CreateJobUserRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                webVagasUtil.getUrl(), HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}