package com.webvagas.api.webvagas_api.application.service.user_preferences.command;

import com.webvagas.api.webvagas_api.domain.value_object.UserId;

public record DeleteUserPreferencesCommand(
        UserId userId) {
}
