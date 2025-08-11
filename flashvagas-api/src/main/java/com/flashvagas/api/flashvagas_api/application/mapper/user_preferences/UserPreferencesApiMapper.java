package com.flashvagas.api.flashvagas_api.application.mapper.user_preferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserPreferencesId;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.UserPreferences;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.update.UserPreferencesUpdateRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.flashvagas.api.flashvagas_api.domain.value_object.Country;
import com.flashvagas.api.flashvagas_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.flashvagas_api.domain.value_object.ExcludeJobPublishers;

@Mapper(componentModel = "spring", imports = {
        UUID.class, UserPreferencesId.class, Country.class, EmploymentTypes.class,
        ExcludeJobPublishers.class })
public interface UserPreferencesApiMapper {
    @Mapping(target = "id", expression = "java(new UserPreferencesId(UUID.randomUUID()))")
    @Mapping(target = "userId", expression = "java(new UserId(UUID.fromString(request.userId())))")
    @Mapping(target = "keywords", expression = "java(new Keywords(request.keywords()))")
    @Mapping(target = "employmentTypes", expression = "java(mapToEmploymentTypes(request.employmentTypes()))")
    @Mapping(target = "searchFilters", expression = "java(new SearchFilters(request.remoteWork(), new Country(request.country()), new ExcludeJobPublishers(request.excludeJobPublishers())))")
    CreateUserPreferencesCommand createRequestToCommand(UserPreferencesCreateRequest request);

    @Mapping(target = "keywords", expression = "java(domain.getKeywords().getValue())")
    @Mapping(target = "employmentTypes", expression = "java(domain.getEmploymentTypes().toString())")
    @Mapping(target = "country", expression = "java(domain.getSearchFiltersCountry().getValue())")
    @Mapping(target = "remoteWork", expression = "java(domain.getSearchFiltersRemoteWork())")
    @Mapping(target = "excludeJobPublishers", expression = "java(domain.getSearchFiltersExcludeJobPublishers().getValue())")
    UserPreferencesCreateResponse domainToApiCreateResponse(UserPreferences domain);

    @Mapping(target = "keywords", expression = "java(domain.getKeywords().getValue())")
    @Mapping(target = "employmentTypes", expression = "java(domain.getEmploymentTypes().toString())")
    @Mapping(target = "country", expression = "java(domain.getSearchFiltersCountry().getValue())")
    @Mapping(target = "remoteWork", expression = "java(domain.getSearchFiltersRemoteWork())")
    @Mapping(target = "excludeJobPublishers", expression = "java(domain.getSearchFiltersExcludeJobPublishers().getValue())")
    UserPreferencesGetResponse domainToApiGetResponse(UserPreferences domain);

    @Mapping(target = "userId", expression = "java(new UserId(request.userId()))")
    GetUserPreferencesQuery getRequestToQuery(UserPreferencesGetRequest request);

    @Mapping(target = "userId", expression = "java(new UserId(request.userId()))")
    @Mapping(target = "keywords", expression = "java(new Keywords(request.keywords()))")
    @Mapping(target = "employmentTypes", expression = "java(mapToEmploymentTypes(request.employmentTypes()))")
    @Mapping(target = "searchFilters", expression = "java(new SearchFilters(request.remoteWork(), new Country(request.country()), new ExcludeJobPublishers(request.excludeJobPublishers())))")
    UpdateUserPreferencesCommand updateRequestToCommand(UserPreferencesUpdateRequest request);

    default EmploymentTypes mapToEmploymentTypes(String employmentTypesString) {
        if (employmentTypesString == null || employmentTypesString.isBlank()) {
            return new EmploymentTypes(Collections.emptySet());
        }
        Set<EmploymentType> types = Arrays.stream(employmentTypesString.split(","))
                .map(String::trim)
                .map(EmploymentType::valueOf)
                .collect(Collectors.toSet());
        return new EmploymentTypes(types);
    }

    default String mapToString(EmploymentTypes employmentTypes) {
        if (employmentTypes == null || employmentTypes.values().isEmpty()) {
            return "";
        }
        return employmentTypes.values().stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }
}