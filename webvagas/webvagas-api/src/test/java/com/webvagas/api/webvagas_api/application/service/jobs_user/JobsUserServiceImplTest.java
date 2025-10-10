package com.webvagas.api.webvagas_api.application.service.jobs_user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.webvagas.api.webvagas_api.application.mapper.jobs_user.JobsUserJpaMapper;
import com.webvagas.api.webvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.webvagas.api.webvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.JobsUser;
import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.infrastructure.repository.jobs_user.JobsUserRepository;
import com.webvagas.api.webvagas_api.persistence.jobs_user.JobsUserEntity;
import com.webvagas.api.webvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

public class JobsUserServiceImplTest {
    @Mock
    private JobsUserRepository jobsUserRepository;

    @Mock
    private JobsUserJpaMapper jobsUserJpaMapper;

    private JobsUserServiceImpl jobsUserService;

    @BeforeEach
    void Setup() {
        MockitoAnnotations.openMocks(this);
        jobsUserService = new JobsUserServiceImpl(jobsUserRepository, jobsUserJpaMapper);
    }

    @Test
    void shouldReturnSuccessWhenCreateJobsUser() {
        UUID uuid = UUID.randomUUID();

        UUID uuid2 = UUID.randomUUID();

        CreateJobUserCommand command = mock(CreateJobUserCommand.class);

        UserId userId = mock(UserId.class);

        JobId jobId = mock(JobId.class);

        JobsUser jobsUser = mock(JobsUser.class);

        JobsUserEntity jobsUserEntity = mock(JobsUserEntity.class);

        when(userId.getValue()).thenReturn(uuid);

        when(jobId.getValue()).thenReturn(uuid2.toString());

        when(command.userId()).thenReturn(userId);

        when(command.jobIds()).thenReturn(
                List.of(jobId));

        when(jobsUserJpaMapper.createCommandToDomain(userId, jobId)).thenReturn(jobsUser);

        when(jobsUserJpaMapper.domainToEntity(jobsUser)).thenReturn(jobsUserEntity);

        when(jobsUserRepository.saveAll(List.of(jobsUserEntity))).thenReturn(List.of(jobsUserEntity));

        String result = jobsUserService.createJobsUsers(command);

        assertThat("success").isEqualTo(result);
    }

    
    @Test
    void shouldReturnProjectionOfJobsAlreadyExistsForUser() {
        // given
        UUID id = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        GetJobUserQuery query = mock(GetJobUserQuery.class);
        UserId userId = mock(UserId.class);
        JobId jobId = mock(JobId.class);

        when(userId.getValue()).thenReturn(id);
        when(jobId.getValue()).thenReturn(uuid2.toString());
        when(query.userId()).thenReturn(userId);
        when(query.jobIds()).thenReturn(List.of(jobId));

        JobExistenceProjection projection = mock(JobExistenceProjection.class);

        List<JobExistenceProjection> expectedProjections = List.of(projection);

        when(jobsUserRepository.findJobsUserExists(any(String[].class), eq(id)))
                .thenReturn(expectedProjections);

        List<JobExistenceProjection> result = jobsUserService.jobsUserExists(query);

        assertThat(result).isEqualTo(expectedProjections);
        verify(jobsUserRepository).findJobsUserExists(any(String[].class), eq(id));
    }

    @Test
    void shouldCountJobsUserByUserIdAndInteval() {
        UUID id = UUID.randomUUID();

        OffsetDateTime start = mock(OffsetDateTime.class);

        OffsetDateTime end = mock(OffsetDateTime.class);

        when(jobsUserRepository.countByUserIdAndReceivedAtBetween(any(UUID.class), any(OffsetDateTime.class),
                any(OffsetDateTime.class))).thenReturn(5);

        int result = jobsUserRepository.countByUserIdAndReceivedAtBetween(id, start, end);

        assertThat(5).isEqualTo(result);
    }

    @Test
    void shouldDeleteJobsUserByJobIdAndUserId() {
        UUID id = UUID.randomUUID();

        when(jobsUserRepository.deleteAllByUserId(any(UUID.class))).thenReturn(5);

        int result = jobsUserRepository.deleteAllByUserId(id);

        assertThat(5).isEqualTo(result);
    }
}