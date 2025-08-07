package com.flashvagas.api.admin_api.domain.entity.job.dto;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetJobResponse(
        @JsonProperty("job_id") String id,
        @JsonProperty("employer_name") String employerName,
        @JsonProperty("employer_logo") String employerLogo,
        @JsonProperty("employer_website") String employerWebsite,
        @JsonProperty("employer_linkedin") String employerLinkedin,
        @JsonProperty("job_publisher") String publisherName,
        @JsonProperty("job_title") String jobTitle,
        @JsonProperty("job_description") String jobDescription,
        @JsonProperty("job_posted_human_readable") String jobsPostedHumanReadable,
        @JsonProperty("job_posted_at_datetime_utc") OffsetDateTime jobPostedDateTime,
        @JsonProperty("job_posted_timestamp") Timestamp jobPostedTimestamp,
        @JsonProperty("job_is_remote") Boolean jobIsRemote,
        @JsonProperty("job_apply_link") String jobApplyLink,
        @JsonProperty("job_employment_types") String[] jobEmploymentTypes,
        @JsonProperty("job_languages") String jobLocationRegion,
        @JsonProperty("job_location") String jobLocationCity,
        @JsonProperty("job_location_state") String jobLocationState,
        @JsonProperty("job_location_country") String jobLocationCountry,
        @JsonProperty("job_location_latitude") Double jobLatitude,
        @JsonProperty("job_location_longitude") Double jobLongitude,
        @JsonProperty("job_salary") Integer jobSalary,
        @JsonProperty("job_min_salary") Integer jobMin,
        @JsonProperty("job_max_salary") Integer jobMax,
        @JsonProperty("job_salary_currency") String jobCurrency,
        @JsonProperty("job_salary_period") String jobPeriod) {

}
