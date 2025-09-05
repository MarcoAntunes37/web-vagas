package com.flashvagas.api.admin_api.domain.entity.url_shortener.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateUrlShortenerResponse(
        @JsonProperty("code") String code,
        @JsonProperty("originalUrl") String originalUrl) {
}
