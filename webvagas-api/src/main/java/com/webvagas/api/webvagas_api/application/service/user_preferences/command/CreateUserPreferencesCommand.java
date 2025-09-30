package com.webvagas.api.webvagas_api.application.service.user_preferences.command;

import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;
import com.webvagas.api.webvagas_api.domain.value_object.SearchFilters;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;

public record CreateUserPreferencesCommand(
        UserPreferencesId id,
        UserId userId,
        Keywords keywords,
        EmploymentTypes employmentTypes,
        SearchFilters searchFilters) {
}