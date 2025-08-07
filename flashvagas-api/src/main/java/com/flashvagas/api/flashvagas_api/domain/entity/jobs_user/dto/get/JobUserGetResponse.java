package com.flashvagas.api.flashvagas_api.domain.entity.jobs_user.dto.get;

import java.time.OffsetDateTime;
import java.util.UUID;

public record JobUserGetResponse(
        UUID userId,
        String jobId,
        OffsetDateTime receivedAt) {
}