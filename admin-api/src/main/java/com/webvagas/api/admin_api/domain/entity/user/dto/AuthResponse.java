package com.webvagas.api.admin_api.domain.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthResponse(
                @JsonProperty("access_token") String accessToken,
                @JsonProperty("refresh_token") String refreshToken,
                @JsonProperty("refresh_expires_in") String refreshExpiresIn,
                @JsonProperty("expires_in") Long expiresIn,
                @JsonProperty("token_type") String tokenType,
                @JsonProperty("not-before-policy") Long notBeforePolicy,
                @JsonProperty("session_state") String sessionState,
                @JsonProperty("scope") String scope) {
}