package com.flashvagas.api.flashvagas_api.api.controller.jobs_user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flashvagas.api.flashvagas_api.application.mapper.jobs_user.JobsUserApiMapper;
import com.flashvagas.api.flashvagas_api.application.service.jobs_user.JobsUserService;
import com.flashvagas.api.flashvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.flashvagas.api.flashvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.flashvagas.api.flashvagas_api.domain.entity.jobs_user.dto.create.JobUserCreateRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.jobs_user.dto.get.JobUserGetRequest;
import com.flashvagas.api.flashvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

@RestController
@RequestMapping("api/v1/jobs/user")
public class JobsUserController {
    private final JobsUserService jobsUserService;

    @Autowired
    private JobsUserApiMapper jobsUserApiMapper;

    public JobsUserController(
            JobsUserService jobsUserService) {
        this.jobsUserService = jobsUserService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<JobExistenceProjection>> exists(
            @PathVariable UUID userId,
            @RequestParam List<String> jobIds) {
        JobUserGetRequest request = new JobUserGetRequest(userId, jobIds);

        GetJobUserQuery query = jobsUserApiMapper
                .getRequesttoQuery(request);

        List<JobExistenceProjection> response = jobsUserService
                .jobsUserExists(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<String> create(
            @RequestBody JobUserCreateRequest request) {
        CreateJobUserCommand command = jobsUserApiMapper
                .createRequestToCommand(request);

        String response = jobsUserService
                .createJobsUsers(command);

        return ResponseEntity.ok(response);
    }
}
