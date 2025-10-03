package com.webvagas.api.admin_api.domain.entity.job.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetJobsResponse(
                @JsonProperty("status") String status,
                @JsonProperty("request_id") UUID requestId,
                @JsonProperty("parameters") Parameters parameters,
                @JsonProperty("data") List<GetJobResponse> data) {
}
