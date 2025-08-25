package com.flashvagas.api.flashvagas_api.application.mapper.jobs_user;

import java.time.OffsetDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.domain.value_object.JobId;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserId;
import com.flashvagas.api.flashvagas_api.persistence.jobs_user.JobsUserEntity;
import com.flashvagas.api.flashvagas_api.persistence.jobs_user.keys.JobsUserKey;

@Mapper(componentModel = "spring", imports = { OffsetDateTime.class })
public interface JobsUserJpaMapper {
    @Mapping(target = "userId", expression = "java(userId.getValue())")
    @Mapping(target = "jobId", expression = "java(jobId.getValue())")
    JobsUserKey createCommandToKey(UserId userId, JobId jobId);

    @Mapping(target = "id.jobId", source = "key.jobId")
    @Mapping(target = "id.userId", source = "key.userId")
    @Mapping(target = "receivedAt", expression = "java(OffsetDateTime.now())")
    JobsUserEntity createKeyToEntity(JobsUserKey key);
}
