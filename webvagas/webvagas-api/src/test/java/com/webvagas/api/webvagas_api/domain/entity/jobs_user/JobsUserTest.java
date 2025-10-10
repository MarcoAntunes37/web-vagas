package com.webvagas.api.webvagas_api.domain.entity.jobs_user;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;

public class JobsUserTest {
    private JobId jobId;
    private UserId userId;
    private OffsetDateTime receivedAt;

    @BeforeEach
    void Setup() {
        jobId = new JobId("1234567890");
        userId = new UserId(UUID.randomUUID());
        receivedAt = OffsetDateTime.now();
    }

    @Test
    void shouldcreateJobsUserCorrectly() {
        JobsUser jobsUser = new JobsUser(jobId, userId, receivedAt);
        assertThat(jobsUser.getJobId()).isEqualTo(jobId);
        assertThat(jobsUser.getUserId()).isEqualTo(userId);
        assertThat(jobsUser.getReceivedAt()).isEqualTo(receivedAt);
    }
}