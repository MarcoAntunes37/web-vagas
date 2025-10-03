package com.webvagas.api.webvagas_api.infrastructure.repository.jobs_user;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webvagas.api.webvagas_api.persistence.jobs_user.JobsUserEntity;
import com.webvagas.api.webvagas_api.persistence.jobs_user.keys.JobsUserKey;
import com.webvagas.api.webvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

import jakarta.transaction.Transactional;

public interface JobsUserRepository extends JpaRepository<JobsUserEntity, JobsUserKey> {
        @Query(value = """
                        SELECT u.job_id,
                               COUNT(lju.job_id) > 0 AS exists
                        FROM unnest(:jobIds) AS u(job_id)
                        LEFT JOIN jobs_user lju
                          ON u.job_id = lju.job_id AND lju.user_id = :userId
                        GROUP BY u.job_id
                        """, nativeQuery = true)
        List<JobExistenceProjection> findJobsUserExists(
                        @Param("jobIds") String[] jobIds,
                        @Param("userId") UUID userId);

        @Query(value = """
                        SELECT COUNT(*)
                        FROM jobs_user
                        WHERE user_id = :userId
                        AND received_at BETWEEN :start AND :end
                        """, nativeQuery = true)
        int countByUserIdAndReceivedAtBetween(UUID userId, OffsetDateTime start, OffsetDateTime end);

        @Query(value = """
                        DELETE FROM jobs_user
                        WHERE user_id = :userId
                        """, nativeQuery = true)
        @Modifying
        @Transactional
        int deleteAllByUserId(@Param("userId") UUID userId);
}