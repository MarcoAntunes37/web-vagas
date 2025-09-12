package com.flashvagas.api.admin_api.api.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobResponse;
import com.flashvagas.api.admin_api.domain.entity.job.dto.GetJobsResponse;

@RestController
@RequestMapping("/mock")
public class JobMockController {
    @GetMapping("/jobs")
    public ResponseEntity<GetJobsResponse> getJobsMock() {
        OffsetDateTime now = OffsetDateTime.now();
        GetJobsResponse mockResponse = new GetJobsResponse(
                "success",
                null,
                null,
                List.of(
                        new GetJobResponse("job_id", "employer_name", "Desenvolvedor Full Stack", now,
                                "https://randomjobs.com.br/jobs/123456"),
                        new GetJobResponse("job_id", "employer_name", "Desenvolvedor Full Stack", now,
                                "https://randomjobs.com.br/jobs/123456"),
                        new GetJobResponse("job_id", "employer_name", "Desenvolvedor Full Stack", now,
                                "https://randomjobs.com.br/jobs/123456")));
                                
        return ResponseEntity.ok(mockResponse);
    }
}
