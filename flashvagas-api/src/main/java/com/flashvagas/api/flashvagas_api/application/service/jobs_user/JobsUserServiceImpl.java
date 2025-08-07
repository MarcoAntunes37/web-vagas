package com.flashvagas.api.flashvagas_api.application.service.jobs_user;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashvagas.api.flashvagas_api.application.mapper.jobs_user.JobsUserJpaMapper;
import com.flashvagas.api.flashvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.flashvagas.api.flashvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.flashvagas.api.flashvagas_api.infrastructure.repository.jobs_user.JobsUserRepository;
import com.flashvagas.api.flashvagas_api.persistence.jobs_user.JobsUserEntity;
import com.flashvagas.api.flashvagas_api.persistence.jobs_user.keys.JobsUserKey;
import com.flashvagas.api.flashvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

@Service
public class JobsUserServiceImpl implements JobsUserService {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(JobsUserServiceImpl.class);

    private JobsUserRepository jobsUserRepository;

    @Autowired
    private JobsUserJpaMapper jobsUserJpaMapper;

    public JobsUserServiceImpl(
            JobsUserRepository jobsUserRepository) {
        this.jobsUserRepository = jobsUserRepository;
    }

    public String createJobsUsers(CreateJobUserCommand command) {
        List<JobsUserEntity> jobsUsers = command
                .jobIds()
                .stream()
                .map(jobId -> {
                    JobsUserKey key = jobsUserJpaMapper.createCommandToKey(command.userId(), jobId);

                    return jobsUserJpaMapper.createKeyToEntity(key);
                })
                .toList();

        jobsUserRepository.saveAll(jobsUsers);

        return "success";
    }

    public List<JobExistenceProjection> jobsUserExists(GetJobUserQuery query) {
        UUID userId = query.userId().getValue();

        List<String> jobIds = query.jobIds()
                .stream()
                .map(jobId -> jobId.getValue())
                .toList();

        String[] arrayJobs = jobIds.toArray(new String[jobIds.size()]);

        return jobsUserRepository.findJobsUserExists(arrayJobs, userId);
    }

    public int deleteJobsUserByUserId(UUID userId) {
        int deletedLines = 0;
        try {
            deletedLines = jobsUserRepository.deleteAllByUserId(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return deletedLines;
    }

}