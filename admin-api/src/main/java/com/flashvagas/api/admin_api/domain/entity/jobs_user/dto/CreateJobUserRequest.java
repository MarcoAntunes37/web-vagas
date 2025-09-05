package com.flashvagas.api.admin_api.domain.entity.jobs_user.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateJobUserRequest(
                @JsonProperty("userId") String userId,
                @JsonProperty("jobIds") List<String> jobIds) {
}
