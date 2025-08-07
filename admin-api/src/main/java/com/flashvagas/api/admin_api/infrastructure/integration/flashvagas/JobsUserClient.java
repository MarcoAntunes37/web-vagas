package com.flashvagas.api.admin_api.infrastructure.integration.flashvagas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.jobs_user.dto.CreateJobUserRequest;
import com.flashvagas.api.admin_api.domain.entity.jobs_user.dto.JobsUserResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.utils.FlashVagasUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobsUserClient {
    private final RestTemplate restTemplate;

    @Autowired
    private FlashVagasUtil flashVagasUtil;

    public ResponseEntity<List<JobsUserResponse>> getJobsUser(String userId, List<String> jobsIds, String token) {
        ResponseEntity<List<JobsUserResponse>> response = null;

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        headers.set("Authorization", "Bearer " + token);

        HttpEntity<List<JobsUserResponse>> entity = new HttpEntity<>(headers);

        String fullUrl = flashVagasUtil.buildUrl(userId, jobsIds);

        response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<JobsUserResponse>>() {
                });

        return response;
    }

    public String createJobUser(String userId, List<String> jobsIds, String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");

        headers.set("Authorization", "Bearer " + token);

        CreateJobUserRequest request = new CreateJobUserRequest(userId, jobsIds);

        HttpEntity<CreateJobUserRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                flashVagasUtil.getUrl(), HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}