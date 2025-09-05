package com.flashvagas.api.admin_api.domain.entity.url_shortener.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateUrlShortenerRequest(
        @JsonProperty("originalUrl") String originalUrl) {
}
