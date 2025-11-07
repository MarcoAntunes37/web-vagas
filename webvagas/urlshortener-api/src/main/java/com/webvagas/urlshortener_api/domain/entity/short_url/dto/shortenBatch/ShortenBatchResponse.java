package com.webvagas.urlshortener_api.domain.entity.short_url.dto.shortenBatch;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webvagas.urlshortener_api.infrastructure.repository.projections.UrlShortenerBatchInsertProjection;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShortenBatchResponse(
                @JsonProperty("shortenedUrl") List<UrlShortenerBatchInsertProjection> shortenedUrl) {
}
