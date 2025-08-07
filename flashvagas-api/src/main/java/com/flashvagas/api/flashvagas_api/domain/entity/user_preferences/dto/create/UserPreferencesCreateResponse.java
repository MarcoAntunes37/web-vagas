package com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.create;

public record UserPreferencesCreateResponse(
        String keywords,
        String employmentTypes,
        String country,
        Boolean remoteWork,
        String excludeJobPublishers) {
}