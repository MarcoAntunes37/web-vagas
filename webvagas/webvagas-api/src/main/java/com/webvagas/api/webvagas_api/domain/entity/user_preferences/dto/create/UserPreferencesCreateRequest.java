package com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create;

public record UserPreferencesCreateRequest(
                String userId,
                String keywords,
                String employmentTypes,
                Boolean remoteWork,
                String country,
                String excludeJobPublishers) {
}