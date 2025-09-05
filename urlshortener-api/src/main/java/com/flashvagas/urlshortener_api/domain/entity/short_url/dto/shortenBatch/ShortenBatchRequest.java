package com.flashvagas.urlshortener_api.domain.entity.short_url.dto.shortenBatch;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShortenBatchRequest(
        @JsonProperty("originalUrls") List<String> originalUrls) {
}
