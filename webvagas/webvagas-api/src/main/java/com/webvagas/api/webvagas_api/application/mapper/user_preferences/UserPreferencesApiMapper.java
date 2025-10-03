package com.webvagas.api.webvagas_api.application.mapper.user_preferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.UserPreferences;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.update.UserPreferencesUpdateRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;

@Mapper(componentModel = "spring", imports = {
        UUID.class, UserPreferencesId.class, Country.class, EmploymentTypes.class,
        ExcludeJobPublishers.class })
public interface UserPreferencesApiMapper {
    @Mapping(target = "id", expression = "java(new UserPreferencesId(UUID.randomUUID()))")
    @Mapping(target = "userId", expression = "java(new UserId(userId))")
    @Mapping(target = "keywords", expression = "java(new Keywords(request.keywords()))")
    @Mapping(target = "employmentTypes", expression = "java(mapStringToEmploymentTypes(request.employmentTypes()))")
    @Mapping(target = "searchFilters", expression = "java(new SearchFilters(request.remoteWork(), new Country(request.country()), mapStringToExcludeJobPublishers(request.excludeJobPublishers())))")
    CreateUserPreferencesCommand createRequestToCommand(UUID userId, UserPreferencesCreateRequest request);

    @Mapping(target = "keywords", expression = "java(domain.getKeywords().getValue())")
    @Mapping(target = "employmentTypes", expression = "java(mapEmploymentTypesToString(domain.getEmploymentTypes()))")
    @Mapping(target = "country", expression = "java(domain.getSearchFiltersCountry().getValue())")
    @Mapping(target = "remoteWork", expression = "java(domain.getSearchFiltersRemoteWork())")
    @Mapping(target = "excludeJobPublishers", expression = "java(mapExcludeJobPublishersToString(domain.getSearchFiltersExcludeJobPublishers()))")
    UserPreferencesCreateResponse domainToApiCreateResponse(UserPreferences domain);

    @Mapping(target = "keywords", expression = "java(domain.getKeywords().getValue())")
    @Mapping(target = "employmentTypes", expression = "java(mapEmploymentTypesToString(domain.getEmploymentTypes()))")
    @Mapping(target = "country", expression = "java(domain.getSearchFiltersCountry().getValue())")
    @Mapping(target = "remoteWork", expression = "java(domain.getSearchFiltersRemoteWork())")
    @Mapping(target = "excludeJobPublishers", expression = "java(mapExcludeJobPublishersToString(domain.getSearchFiltersExcludeJobPublishers()))")
    UserPreferencesGetResponse domainToApiGetResponse(UserPreferences domain);

    @Mapping(target = "userId", expression = "java(new UserId(request.userId()))")
    GetUserPreferencesQuery getRequestToQuery(UserPreferencesGetRequest request);

    @Mapping(target = "userId", expression = "java(new UserId(userId))")
    @Mapping(target = "keywords", expression = "java(new Keywords(request.keywords()))")
    @Mapping(target = "employmentTypes", expression = "java(mapStringToEmploymentTypes(request.employmentTypes()))")
    @Mapping(target = "searchFilters", expression = "java(new SearchFilters(request.remoteWork(), new Country(request.country()), mapStringToExcludeJobPublishers(request.excludeJobPublishers())))")
    UpdateUserPreferencesCommand updateRequestToCommand(UUID userId, UserPreferencesUpdateRequest request);

    default EmploymentTypes mapStringToEmploymentTypes(String employmentTypesString) {
        if (employmentTypesString == null || employmentTypesString.isBlank()) {
            return new EmploymentTypes(Collections.emptySet());
        }
        Set<EmploymentType> types = Arrays.stream(employmentTypesString.split(","))
                .map(String::trim)
                .map(EmploymentType::valueOf)
                .collect(Collectors.toSet());
        return new EmploymentTypes(types);
    }

    default String mapEmploymentTypesToString(EmploymentTypes employmentTypes) {
        if (employmentTypes == null || employmentTypes.values().isEmpty()) {
            return "";
        }
        return employmentTypes.values().stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    default String mapExcludeJobPublishersToString(ExcludeJobPublishers excludeJobPublishers) {
        if (excludeJobPublishers == null || excludeJobPublishers.getValue().isEmpty()) {
            return "";
        }

        return excludeJobPublishers.getValue().stream()
                .collect(Collectors.joining(","));
    }

    default ExcludeJobPublishers mapStringToExcludeJobPublishers(String excludeJobPublishersString) {
        if (excludeJobPublishersString == null || excludeJobPublishersString.isBlank()) {
            return new ExcludeJobPublishers(Collections.emptySet());
        }
        return new ExcludeJobPublishers(
                Arrays.stream(excludeJobPublishersString.split(","))
                        .collect(Collectors.toSet()));
    }
}