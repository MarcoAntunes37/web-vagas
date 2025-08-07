package com.flashvagas.api.admin_api.domain.entity.jobs_user.dto;

public record JobsUserResponse(
        boolean exists,
        String jobId) {
}
