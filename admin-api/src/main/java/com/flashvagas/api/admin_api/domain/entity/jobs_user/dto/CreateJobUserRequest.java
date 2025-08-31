package com.flashvagas.api.admin_api.domain.entity.jobs_user.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateJobUserRequest(
                String userId,
                List<String> jobIds) {
}
