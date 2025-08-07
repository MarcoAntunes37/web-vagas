package com.flashvagas.api.admin_api.infrastructure.integration.jsearch;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsRequest;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.jsearch.utils.JSearchUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JSearchClient {
    private final RestTemplate restTemplate;
    @Autowired
    private JSearchUtils jsearchUtils;
    @Value("${jsearch.rapidapi-host}")
    private String rapidapiHost;
    @Value("${jsearch.rapidapi-key}")
    private String rapidapiKey;

    public ResponseEntity<GetJobsResponse> getJobs(GetJobsRequest request) {
        ResponseEntity<GetJobsResponse> response = null;
        try {
            HashMap<String, Object> params = jsearchUtils.createParams(request);

            String fullUrl = jsearchUtils.createUrl(params);

            HttpHeaders headers = new HttpHeaders();

            headers.set("Content-Type", "application/json");

            headers.set("x-rapidapi-host", rapidapiHost);

            headers.set("x-rapidapi-key", rapidapiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            response = this.restTemplate.exchange(
                    fullUrl, HttpMethod.GET, entity, GetJobsResponse.class);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erro ao buscar vagas: " + e.getMessage());
        }

        return response;
    }
}
