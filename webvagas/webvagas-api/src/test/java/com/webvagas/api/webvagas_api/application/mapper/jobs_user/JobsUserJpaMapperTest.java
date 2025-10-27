package com.webvagas.api.webvagas_api.application.mapper.jobs_user;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;

import com.webvagas.api.webvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.JobsUser;
import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.persistence.jobs_user.JobsUserEntity;
import com.webvagas.api.webvagas_api.persistence.jobs_user.keys.JobsUserKey;

public class JobsUserJpaMapperTest {
    private JobsUserJpaMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(JobsUserJpaMapper.class);
    }

    @Test
    void shouldMapJobsUserCreateCommandToJobsUserEntityCorrectly() {
        UUID jobIdUuid = UUID.randomUUID();

        UUID userIdUuid = UUID.randomUUID();

        UserId userId = new UserId(userIdUuid);

        CreateJobUserCommand command = new CreateJobUserCommand(userId, List.of(new JobId(jobIdUuid.toString())));

        JobsUser jobsUser = mapper.createCommandToDomain(command.userId(), command.jobIds().get(0));

        assertThat(jobsUser).isNotNull();
        assertThat(jobsUser.getUserId()).isEqualTo(userId);
    }

    @Test
    void shouldMapJobsUserDomainToEntityCorrectly() {
        UUID jobIdUuid = UUID.randomUUID();

        UUID userIdUuid = UUID.randomUUID();

        UserId userId = new UserId(userIdUuid);

        JobsUser jobsUser = new JobsUser(new JobId(jobIdUuid.toString()), userId, OffsetDateTime.now());

        JobsUserEntity jobsUserEntity = mapper.domainToEntity(jobsUser);

        assertThat(jobsUserEntity).isNotNull();
        assertThat(jobsUserEntity.getId().getUserId()).isEqualTo(userIdUuid);
    }

    @Test
    void shouldMapJobsUserEntityToDomainCorrectly() {
        UUID jobIdUuid = UUID.randomUUID();

        UUID userIdUuid = UUID.randomUUID();

        UserId userId = new UserId(userIdUuid);

        JobsUserEntity jobsUserEntity = new JobsUserEntity(new JobsUserKey(userIdUuid, jobIdUuid.toString()),
                OffsetDateTime.now());

        JobsUser jobsUser = mapper.entityToDomain(jobsUserEntity);

        assertThat(jobsUser).isNotNull();
        assertThat(jobsUser.getUserId()).isEqualTo(userId);
    }
}