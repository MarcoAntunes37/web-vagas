package com.webvagas.api.admin_api.domain.entity.job.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.webvagas.api.admin_api.domain.entity.job.enums.DatePosted;
import com.webvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.admin_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.admin_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.admin_api.domain.value_object.ExcludeJobPublishers;

public record GetJobsRequest(
                String query,
                Integer page,
                Integer numPages,
                String country,
                String language,
                DatePosted datePosted,
                Boolean isRemote,
                EmploymentTypes employmentTypes,
                ExcludeJobPublishers excludeJobPublishers) {

        public static GetJobsRequest simple(
                        String query,
                        Integer page) {
                return new GetJobsRequest(
                                query,
                                page,
                                1,
                                null,
                                null,
                                DatePosted.today,
                                null,
                                null,
                                null);
        }

        public static GetJobsRequest complete(
                        Integer page,
                        UserPreferencesGetResponse preferences) {
                String keywords = preferences.keywords();
                EmploymentTypes employmentTypesObj = new EmploymentTypes(Arrays.stream(
                        preferences.employmentTypes().split(","))
                                .filter(p -> !p.isBlank())
                                .map(String::trim)
                                .map(EmploymentType::valueOf)
                                .collect(Collectors.toSet()));
                String country = preferences.country();
                Boolean remoteWork = preferences.remoteWork();
                ExcludeJobPublishers excludeJobPublishersObj = new ExcludeJobPublishers(Arrays.stream(
                                preferences.excludeJobPublishers().split(","))
                                .filter(p -> !p.isBlank())
                                .map(String::trim)
                                .collect(Collectors.toSet()));

                return new GetJobsRequest(
                                keywords,
                                page,
                                1,
                                country,
                                null,
                                DatePosted.today,
                                remoteWork,
                                employmentTypesObj,
                                excludeJobPublishersObj);
        }
}