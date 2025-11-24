package com.webvagas.api.webvagas_api.api.controller.jobs_user;

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

import com.webvagas.api.webvagas_api.application.mapper.jobs_user.JobsUserApiMapper;
import com.webvagas.api.webvagas_api.application.service.jobs_user.JobsUserService;
import com.webvagas.api.webvagas_api.application.service.jobs_user.command.CreateJobUserCommand;
import com.webvagas.api.webvagas_api.application.service.jobs_user.query.GetJobUserQuery;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.dto.create.JobUserCreateRequest;
import com.webvagas.api.webvagas_api.domain.entity.jobs_user.dto.get.JobUserGetRequest;
import com.webvagas.api.webvagas_api.persistence.jobs_user.projections.JobExistenceProjection;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Jobs User",  description = "Jobs user relationship management")
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

        @GetMapping("/{userId}/count")
        public ResponseEntity<Integer> count(@PathVariable UUID userId) {
                Integer count = jobsUserService.countJobsUserByUserId(userId);

                return ResponseEntity.ok(count);
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
