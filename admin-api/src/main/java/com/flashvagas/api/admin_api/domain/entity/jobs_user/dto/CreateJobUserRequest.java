package com.flashvagas.api.admin_api.domain.entity.jobs_user.dto;

import java.util.List;

public record CreateJobUserRequest(
        String userId,
        List<String> jobIds) {
}
