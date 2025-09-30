package com.webvagas.api.webvagas_api.application.service.user_preferences;

import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.DeleteUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;

public interface UserPreferencesService {
    UserPreferencesGetResponse findByUserId(GetUserPreferencesQuery query);

    UserPreferencesCreateResponse create(CreateUserPreferencesCommand command);

    void update(UpdateUserPreferencesCommand command);

    int deleteAllByUserId(DeleteUserPreferencesCommand command);
}
