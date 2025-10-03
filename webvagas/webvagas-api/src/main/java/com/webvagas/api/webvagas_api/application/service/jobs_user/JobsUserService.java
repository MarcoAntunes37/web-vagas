package com.webvagas.api.webvagas_api.application.service.jobs_user;

import java.util.List;
import java.util.UUID;

import com.webvagas.api.webvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.webvagas.api.webvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.webvagas.api.webvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

public interface JobsUserService {
    String createJobsUsers(CreateJobUserCommand command);

    List<JobExistenceProjection> jobsUserExists(GetJobUserQuery query);

    int deleteJobsUserByUserId(UUID userId);

    int countJobsUserByUserId(UUID userId);
}
