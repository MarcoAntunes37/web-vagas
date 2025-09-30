package com.webvagas.api.webvagas_api.application.service.jobs_user;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webvagas.api.webvagas_api.application.mapper.jobs_user.JobsUserJpaMapper;
import com.webvagas.api.webvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.webvagas.api.webvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.webvagas.api.webvagas_api.infrastructure.repository.jobs_user.JobsUserRepository;
import com.webvagas.api.webvagas_api.persistence.jobs_user.JobsUserEntity;
import com.webvagas.api.webvagas_api.persistence.jobs_user.keys.JobsUserKey;
import com.webvagas.api.webvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

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

    public int countJobsUserByUserId(UUID userId) {
        OffsetDateTime start = OffsetDateTime.now().withHour(10);

        OffsetDateTime end = OffsetDateTime.now().withHour(17);

        return jobsUserRepository.countByUserIdAndReceivedAtBetween(userId, start, end);
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