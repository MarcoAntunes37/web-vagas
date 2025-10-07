package com.webvagas.api.webvagas_api.persistence.jobs_user.keys;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class JobsUserKey implements Serializable {
    private UUID userId;
    private String jobId;

    public JobsUserKey() {
    }

    public JobsUserKey(UUID userId, String jobId) {
        this.userId = userId;
        this.jobId = jobId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}