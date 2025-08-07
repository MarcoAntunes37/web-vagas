package com.flashvagas.api.flashvagas_api.application.mapper.user_preferences;

public record UserPreferencesCreateRequest(
                String userId,
                String keywords,
                String employmentTypes,
                Boolean remoteWork,
                String country,
                String excludeJobPublishers) {
}