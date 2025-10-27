package com.webvagas.api.admin_api.domain.entity.jobs_user;

import java.time.OffsetDateTime;

import com.webvagas.api.admin_api.domain.value_object.JobId;
import com.webvagas.api.admin_api.domain.value_object.UserId;

public class JobsUser {
    private JobId jobId;
    private UserId userId;
    private OffsetDateTime receivedAt;

    public JobsUser(
            JobId jobId,
            UserId userId,
            OffsetDateTime receivedAt) {
        this.jobId = jobId;
        this.userId = userId;
        this.receivedAt = receivedAt;
    }

    public JobId getJobId() {
        return jobId;
    }

    public UserId getUserId() {
        return userId;
    }

    public OffsetDateTime getReceivedAt() {
        return receivedAt;
    }
}