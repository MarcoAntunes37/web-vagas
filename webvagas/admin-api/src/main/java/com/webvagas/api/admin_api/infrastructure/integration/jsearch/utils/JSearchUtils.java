package com.webvagas.api.admin_api.infrastructure.integration.jsearch.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.webvagas.api.admin_api.domain.entity.job.dto.GetJobsRequest;

@Component
public class JSearchUtils {
    @Value("${jsearch.url}")
    private String url;

    final Integer numPages = 1;

    public String createUrl(
            Map<String, Object> params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        boolean firstParam = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                if (firstParam) {
                    urlBuilder.append("?");
                    firstParam = false;
                } else {
                    urlBuilder.append("&");
                }
                urlBuilder.append(key)
                        .append("=")
                        .append(value.toString());
            }
        }

        return urlBuilder.toString();
    }

    public HashMap<String, Object> createParams(GetJobsRequest request) {
        HashMap<String, Object> params = new LinkedHashMap<>();

        putIfNotNullNotBlank(params, "query", request.query());
        putIfNotNullNotBlank(params, "page", request.page());
        putIfNotNullNotBlank(params, "num_pages", request.numPages());
        putIfNotNullNotBlank(params, "country", request.country());
        putIfNotNullNotBlank(params, "date_posted", request.datePosted());
        putIfNotNullNotBlank(params, "work_from_home", request.isRemote());
        putIfNotNullNotBlank(params, "employment_types", request.employmentTypes());
        putIfNotNullNotBlank(params, "exclude_job_publishers", request.excludeJobPublishers().getValue());
        putIfNotNullNotBlank(params, "fields", "job_id, employer_name, job_title, job_apply_link,  job_posted_at_datetime_utc");

        return params;
    }

    private void putIfNotNullNotBlank(Map<String, Object> map, String key, Object value) {
        if (value != null && !value.toString().isBlank()) {
            map.put(key, value);
        }
    }
}
