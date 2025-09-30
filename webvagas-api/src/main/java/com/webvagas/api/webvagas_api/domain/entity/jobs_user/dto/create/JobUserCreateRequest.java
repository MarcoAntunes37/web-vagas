package com.webvagas.api.webvagas_api.domain.entity.jobs_user.dto.create;

import java.util.List;
import java.util.UUID;

public record JobUserCreateRequest(
        UUID userId,
        List<String> jobIds) {
}