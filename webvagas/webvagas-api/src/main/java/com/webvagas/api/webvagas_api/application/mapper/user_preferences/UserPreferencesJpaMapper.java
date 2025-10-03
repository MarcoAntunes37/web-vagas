package com.webvagas.api.webvagas_api.application.mapper.user_preferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.UserPreferences;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.persistence.user_preferences.UserPreferencesEntity;

@Mapper(componentModel = "spring", imports = { Country.class, ExcludeJobPublishers.class })
public interface UserPreferencesJpaMapper {
    @Mapping(target = "keywordsDb", source = "keywords.value")
    @Mapping(target = "employmentTypesDb", expression = "java(mapEmploymentTypesToString(domain.getEmploymentTypes()))")
    @Mapping(target = "excludeJobPublishersDb", expression = "java(mapExcludeJobPublishersToString(domain.getSearchFiltersExcludeJobPublishers()))")
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "keywords", source = "keywords")
    @Mapping(target = "employmentTypes", expression = "java(mapStringToEmploymentTypes(domain.getEmploymentTypes().toString()))")
    @Mapping(target = "country", expression = "java(domain.getSearchFiltersCountry().getValue())")
    @Mapping(target = "remoteWork", expression = "java(domain.getSearchFiltersRemoteWork())")
    @Mapping(target = "excludeJobPublishers", expression = "java(mapStringToExcludeJobPublishers(domain.getSearchFiltersExcludeJobPublishers().toString()))")
    UserPreferencesEntity domainToEntity(UserPreferences domain);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "userId.value", source = "userId")
    @Mapping(target = "keywords", expression = "java(new Keywords(entity.getKeywordsDb()))")
    @Mapping(target = "employmentTypes", expression = "java(mapStringToEmploymentTypes(entity.getEmploymentTypesDb()))")
    @Mapping(target = "searchFilters", expression = "java(new SearchFilters(entity.getRemoteWork(), new Country(entity.getCountry()), mapStringToExcludeJobPublishers(entity.getExcludeJobPublishersDb())))")
    UserPreferences entityToDomain(UserPreferencesEntity entity);

    UserPreferences createCommandToDomain(CreateUserPreferencesCommand command);

    @Mapping(target = "id", ignore = true)
    UserPreferences updateCommandtoDomain(UpdateUserPreferencesCommand command);

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

    default ExcludeJobPublishers mapStringToExcludeJobPublishers(String excludeJobPublishersString) {
        if (excludeJobPublishersString == null || excludeJobPublishersString.isBlank()) {
            return new ExcludeJobPublishers(Collections.emptySet());
        }
        return new ExcludeJobPublishers(
                Arrays.stream(excludeJobPublishersString.split(","))
                        .collect(Collectors.toSet()));
    }

    default String mapExcludeJobPublishersToString(ExcludeJobPublishers excludeJobPublishers) {
        if (excludeJobPublishers == null || excludeJobPublishers.getValue().isEmpty()) {
            return "";
        }
        return excludeJobPublishers.getValue().stream()
                .collect(Collectors.joining(","));
    }
}