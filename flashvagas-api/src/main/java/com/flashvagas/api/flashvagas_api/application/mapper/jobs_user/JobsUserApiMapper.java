package com.flashvagas.api.flashvagas_api.application.mapper.jobs_user;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.flashvagas.api.flashvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.flashvagas.api.flashvagas_api.domain.entity.jobs_user.dto.create.JobUserCreateRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.jobs_user.dto.get.JobUserGetRequest;
import com.flashvagas.api.flashvagas_api.domain.value_object.JobId;

@Mapper(componentModel = "spring", imports = List.class)
public interface JobsUserApiMapper {
    default List<JobId> toJobIds(List<String> ids) {
        return ids.stream()
                .map(JobId::new)
                .toList();
    }

    @Mapping(target = "userId", expression = "java(new UserId(request.userId()))")
    @Mapping(target = "jobIds", expression = "java(toJobIds(request.jobIds()))")
    CreateJobUserCommand createRequestToCommand(JobUserCreateRequest request);

    @Mapping(target = "userId", expression = "java(new UserId(request.userId()))")
    @Mapping(target = "jobIds", expression = "java(toJobIds(request.jobIds()))")
    GetJobUserQuery getRequesttoQuery(JobUserGetRequest request);
}
