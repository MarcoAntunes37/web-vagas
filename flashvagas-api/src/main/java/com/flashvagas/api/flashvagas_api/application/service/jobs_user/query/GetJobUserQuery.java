package com.flashvagas.api.flashvagas_api.application.service.jobs_user.query;

import java.util.List;

import com.flashvagas.api.flashvagas_api.domain.value_object.JobId;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserId;

public record GetJobUserQuery(
        UserId userId,
        List<JobId> jobIds) {
}
