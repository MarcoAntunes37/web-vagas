package com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.update;

import java.util.UUID;

public record UserPreferencesUpdateRequest(
                UUID userId,
                String keywords,
                String employmentTypes,
                String country,
                Boolean remoteWork,
                String excludeJobPublishers) {
}