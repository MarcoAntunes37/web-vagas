package com.webvagas.api.webvagas_api.application.service.jobs_user.query;

import java.util.List;

import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;

public record GetJobUserQuery(
        UserId userId,
        List<JobId> jobIds) {
}
