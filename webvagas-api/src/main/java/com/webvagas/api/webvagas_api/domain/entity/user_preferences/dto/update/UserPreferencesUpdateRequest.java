package com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.update;

public record UserPreferencesUpdateRequest(
                String keywords,
                String employmentTypes,
                String country,
                Boolean remoteWork,
                String excludeJobPublishers) {
}