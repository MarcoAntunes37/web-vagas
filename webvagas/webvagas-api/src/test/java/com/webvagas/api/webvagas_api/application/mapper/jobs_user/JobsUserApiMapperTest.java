package com.webvagas.api.webvagas_api.application.mapper.jobs_user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.factory.Mappers;

import com.webvagas.api.webvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.webvagas.api.webvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.dto.create.JobUserCreateRequest;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.dto.get.JobUserGetRequest;
import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;

public class JobsUserApiMapperTest {
    private JobsUserApiMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(JobsUserApiMapper.class);
    }

    @Test
    void shouldMapJobsUserCreateRequestToCommandCorrectly() {
        UUID jobIdUuid = UUID.randomUUID();

        UUID userIdUuid = UUID.randomUUID();

        UserId userId = new UserId(userIdUuid);

        JobId jobId = new JobId(jobIdUuid.toString());

        JobUserCreateRequest request = new JobUserCreateRequest(userIdUuid, List.of(jobIdUuid.toString()));

        CreateJobUserCommand command = mapper.createRequestToCommand(request);

        assertThat(command).isNotNull();

        assertThat(command.userId()).isEqualTo(userId);
        assertThat(command.jobIds()).isEqualTo(List.of(jobId));
    }

    @Test
    void shouldMapJobsUserGetRequestToQueryCorrectly() {
        UUID jobIdUuid = UUID.randomUUID();

        UUID userIdUuid = UUID.randomUUID();

        UserId userId = new UserId(userIdUuid);

        JobId jobId = new JobId(jobIdUuid.toString());

        JobUserGetRequest request = new JobUserGetRequest(userIdUuid, List.of(jobIdUuid.toString()));

        GetJobUserQuery query = mapper.getRequesttoQuery(request);

        assertThat(query).isNotNull();

        assertThat(query.userId()).isEqualTo(userId);
        assertThat(query.jobIds()).isEqualTo(List.of(jobId));
    }
}