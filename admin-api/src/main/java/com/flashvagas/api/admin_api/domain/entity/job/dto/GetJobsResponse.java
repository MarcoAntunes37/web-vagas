package com.flashvagas.api.admin_api.domain.entity.job.dto;

import java.util.List;
import java.util.UUID;

public record GetJobsResponse(
        String status,
        UUID requestId,
        Parameters parameters,
        List<GetJobResponse> data) {
}
