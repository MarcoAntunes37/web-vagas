package com.webvagas.urlshortener_api.domain.entity.short_url.dto.redirect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RedirectResponse(
    @JsonProperty("originalUrl") String originalUrl) {
    
}