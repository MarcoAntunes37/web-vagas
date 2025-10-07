package com.webvagas.api.webvagas_api.persistence.jobs_user;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.persistence.jobs_user.keys.JobsUserKey;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

public class JobsUserEntityTest {
    @Test
    void embeddedIdShouldHoldUserAndJobIds() {
        UUID userId = UUID.randomUUID();

        String jobId = UUID.randomUUID().toString();
        
        JobsUserKey key = new JobsUserKey(userId, jobId);

        JobsUserEntity entity = new JobsUserEntity(key, OffsetDateTime.now());

        assertThat(entity.getId().getUserId()).isEqualTo(userId);
        assertThat(entity.getId().getJobId()).isEqualTo(jobId);
    }
}