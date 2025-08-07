package com.flashvagas.api.flashvagas_api.application.service.jobs_user.command;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;
import com.flashvagas.api.flashvagas_api.domain.value_object.JobId;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserId;

public record CreateJobUserCommand(
        UserId userId,
        @JsonValue List<JobId> jobIds) {
}
