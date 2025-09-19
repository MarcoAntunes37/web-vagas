package com.flashvagas.api.flashvagas_api.application.mapper.user_preferences;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flashvagas.api.flashvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.UserPreferences;
import com.flashvagas.api.flashvagas_api.domain.value_object.Country;
import com.flashvagas.api.flashvagas_api.domain.value_object.Keywords;
import com.flashvagas.api.flashvagas_api.persistence.user_preferences.UserPreferencesEntity;
@Mapper(componentModel = "spring", imports = {Country.class})
public interface UserPreferencesJpaMapper {
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "keywords", source = "keywords")
    @Mapping(target = "employmentTypes", source = "employmentTypes")
    @Mapping(target = "country", expression = "java(domain.getSearchFiltersCountry().getValue())")
    @Mapping(target = "remoteWork", expression = "java(domain.getSearchFiltersRemoteWork())")
    @Mapping(target = "excludeJobPublishers", expression = "java(domain.getSearchFiltersExcludeJobPublishers())")
    UserPreferencesEntity domainToEntity(UserPreferences domain);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "userId.value", source = "userId")
    @Mapping(target = "keywords", source = "keywords")
    @Mapping(target = "employmentTypes", source = "employmentTypes")
    @Mapping(target = "searchFilters", expression = "java(new SearchFilters(entity.getRemoteWork(), new Country(entity.getCountry()), entity.getExcludeJobPublishers()))")
    UserPreferences entityToDomain(UserPreferencesEntity entity);

    UserPreferences createCommandToDomain(CreateUserPreferencesCommand command);

    @Mapping(target = "id", ignore = true)
    UserPreferences updateCommandtoDomain(UpdateUserPreferencesCommand command);

    default Keywords map(Optional<Keywords> optional) {
        return optional.orElse(null);
    }
}
