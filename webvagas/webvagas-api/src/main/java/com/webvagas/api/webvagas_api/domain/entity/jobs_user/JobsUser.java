package com.webvagas.api.webvagas_api.domain.entity.jobs_user;

import java.time.OffsetDateTime;

import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;

public class JobsUser {
    private JobId jobId;
    private UserId userId;
    private OffsetDateTime sendedAt;

    public JobsUser(
            JobId jobId,
            UserId userId,
            OffsetDateTime sendedAt) {
        this.jobId = jobId;
        this.userId = userId;
        this.sendedAt = sendedAt;
    }

    public JobId getJobId() {
        return jobId;
    }

    public UserId getUserId() {
        return userId;
    }

    public OffsetDateTime getSendedAt() {
        return sendedAt;
    }
}