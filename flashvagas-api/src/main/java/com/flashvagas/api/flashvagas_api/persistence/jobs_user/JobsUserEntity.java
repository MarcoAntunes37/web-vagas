package com.flashvagas.api.flashvagas_api.persistence.jobs_user;

import java.time.OffsetDateTime;

import com.flashvagas.api.flashvagas_api.persistence.jobs_user.keys.JobsUserKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs_user", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "jobId"}))
public class JobsUserEntity {
    @EmbeddedId
    private JobsUserKey id;
    private OffsetDateTime receivedAt;
}