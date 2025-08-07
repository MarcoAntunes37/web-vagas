package com.flashvagas.api.admin_api.domain.entity.job.dto;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.flashvagas.api.admin_api.domain.entity.job.enums.DatePosted;
import com.flashvagas.api.admin_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.flashvagas.api.admin_api.domain.entity.user_preferences.enums.EmploymentType;
import com.flashvagas.api.admin_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.admin_api.domain.value_object.ExcludeJobPublishers;

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
                String employmentTypes = preferences.employmentTypes();
                String[] employmentTypesArray = employmentTypes.split(",");
                Set<EmploymentType> employmentTypesSet = Arrays.stream(employmentTypesArray)
                                .filter(s -> !s.isBlank())
                                .map(String::trim)
                                .map(EmploymentType::valueOf)
                                .collect(Collectors.toSet());
                EmploymentTypes employmentTypesObj = new EmploymentTypes(employmentTypesSet);
                String country = preferences.country();
                Boolean remoteWork = preferences.remoteWork();
                String excludeJobPublishers = preferences.excludeJobPublishers();

                return new GetJobsRequest(
                                keywords,
                                page,
                                1,
                                country,
                                null,
                                DatePosted.today,
                                remoteWork,
                                employmentTypesObj,
                                ExcludeJobPublishers.toExcludeJobPublishers(excludeJobPublishers));
        }
}
