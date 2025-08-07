package com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.create;

import java.util.UUID;

public record UserPreferencesJsearchCreateRequest(
                UUID userId,
                String keywords,
                String employmentTypes,
                String country,
                Boolean remoteWork,
                String[] excludeJobPublishers) {
}