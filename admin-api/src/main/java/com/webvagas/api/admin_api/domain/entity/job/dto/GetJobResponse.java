package com.webvagas.api.admin_api.domain.entity.job.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetJobResponse(
        @JsonProperty("job_id") String id,
        @JsonProperty("employer_name") String employerName,
        @JsonProperty("job_title") String jobTitle,
        @JsonProperty("job_posted_at_datetime_utc") OffsetDateTime jobPostedDateTime,
        @JsonProperty("job_apply_link") String jobApplyLink) {

}
