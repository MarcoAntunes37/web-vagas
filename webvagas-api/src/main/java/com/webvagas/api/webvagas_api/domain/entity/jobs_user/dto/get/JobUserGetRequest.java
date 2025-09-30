package com.webvagas.api.webvagas_api.domain.entity.jobs_user.dto.get;

import java.util.List;
import java.util.UUID;

public record JobUserGetRequest(
        UUID userId,
        List<String> jobIds) {
}