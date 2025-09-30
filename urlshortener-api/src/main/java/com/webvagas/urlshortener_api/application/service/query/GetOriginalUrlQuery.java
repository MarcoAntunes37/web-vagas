package com.webvagas.urlshortener_api.application.service.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetOriginalUrlQuery(
        @JsonProperty("code") String code) {
}