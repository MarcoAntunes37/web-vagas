package com.webvagas.api.admin_api.domain.entity.jobs_user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JobsUserResponse(
                @JsonProperty("exists") boolean exists,
                @JsonProperty("job_id") String jobId) {
}
