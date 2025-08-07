package com.flashvagas.api.flashvagas_api.application.service.user_preferences.command;

import com.flashvagas.api.flashvagas_api.domain.value_object.EmploymentTypes;
import com.flashvagas.api.flashvagas_api.domain.value_object.Keywords;
import com.flashvagas.api.flashvagas_api.domain.value_object.SearchFilters;
import com.flashvagas.api.flashvagas_api.domain.value_object.UserId;

public record UpdateUserPreferencesCommand(
                UserId userId,
                Keywords keywords,
                EmploymentTypes employmentTypes,
                SearchFilters searchFilters) {
}