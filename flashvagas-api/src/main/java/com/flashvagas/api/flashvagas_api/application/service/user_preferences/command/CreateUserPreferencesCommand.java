package com.flashvagas.api.flashvagas_api.application.service.user_preferences.command;

import com.flashvagas.api.flashvagas_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.flashvagas_api.domain.value_object.Keywords;
import com.flashvagas.api.flashvagas_api.domain.value_object.SearchFilters;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserId;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserPreferencesId;

public record CreateUserPreferencesCommand(
        UserPreferencesId id,
        UserId userId,
        Keywords keywords,
        EmploymentTypes employmentTypes,
        SearchFilters searchFilters) {
}