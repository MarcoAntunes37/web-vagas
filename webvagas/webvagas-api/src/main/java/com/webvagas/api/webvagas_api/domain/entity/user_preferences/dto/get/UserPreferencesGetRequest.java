package com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;

public record UserPreferencesGetRequest(
        @PathVariable("userId") UUID userId) {
}