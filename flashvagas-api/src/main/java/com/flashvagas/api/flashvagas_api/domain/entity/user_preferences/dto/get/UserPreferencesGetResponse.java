package com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.get;

public record UserPreferencesGetResponse(
                String keywords,
                String employmentTypes,
                String country,
                Boolean remoteWork,
                String excludeJobPublishers) {
}