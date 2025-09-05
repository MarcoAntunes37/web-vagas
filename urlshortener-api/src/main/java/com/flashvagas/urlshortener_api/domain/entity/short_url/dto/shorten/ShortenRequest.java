package com.flashvagas.urlshortener_api.domain.entity.short_url.dto.shorten;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShortenRequest(
        @JsonProperty("originalUrl") String originalUrl) {
}
