package com.webvagas.api.webvagas_api.application.mapper.jobs_user;

import java.time.OffsetDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.webvagas.api.webvagas_api.domain.value_object.JobId;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.JobsUser;
import com.webvagas.api.webvagas_api.persistence.jobs_user.JobsUserEntity;
import com.webvagas.api.webvagas_api.persistence.jobs_user.keys.JobsUserKey;

@Mapper(componentModel = "spring", imports = { OffsetDateTime.class, JobsUserKey.class })
public interface JobsUserJpaMapper {
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "jobId", source = "jobId")
    @Mapping(target = "receivedAt", expression = "java(OffsetDateTime.now())")
    JobsUser createCommandToDomain(UserId userId, JobId jobId);

    @Mapping(target = "id", expression = "java(new JobsUserKey(domain.getUserId().getValue(), domain.getJobId().getValue()))")
    @Mapping(target = "receivedAt", expression = "java(domain.getReceivedAt())")
    JobsUserEntity domainToEntity(JobsUser domain);

    @Mapping(target = "userId", expression = "java(new UserId(entity.getUserId()))")
    @Mapping(target = "jobId", expression = "java(new UserId(entity.getJobId()))")
    @Mapping(target = "receivedAt", expression = "java(entity.getReceivedAt())")
    JobsUser entityToDomain(JobsUserEntity entity);
}