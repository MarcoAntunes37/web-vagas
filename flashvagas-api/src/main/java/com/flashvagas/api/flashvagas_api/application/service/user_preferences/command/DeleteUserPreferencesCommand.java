package com.flashvagas.api.flashvagas_api.application.service.user_preferences.command;

import com.flashvagas.api.flashvagas_api.domain.value_object.UserId;

public record DeleteUserPreferencesCommand(
        UserId userId) {
}
