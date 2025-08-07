package com.flashvagas.api.admin_api.infrastructure.integration.flashvagas.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FlashVagasUtil {
    @Value("${jobs-user.url}")
    private String url;

    public String buildUrl(String userId, List<String> jobIds) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .pathSegment(userId);

        for (String jobId : jobIds) {
            builder.queryParam("jobIds", jobId);
        }

        return builder.toUriString();
    }

    public String getUrl() {
        return url;
    }
}