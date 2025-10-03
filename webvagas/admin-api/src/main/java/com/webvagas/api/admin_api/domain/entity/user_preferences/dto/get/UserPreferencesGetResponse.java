package com.webvagas.api.admin_api.domain.entity.user_preferences.dto.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserPreferencesGetResponse(
                @JsonProperty("keywords") String keywords,
                @JsonProperty("employmentTypes") String employmentTypes,
                @JsonProperty("country") String country,
                @JsonProperty("remoteWork") Boolean remoteWork,
                @JsonProperty("excludeJobPublishers") String excludeJobPublishers) {
}