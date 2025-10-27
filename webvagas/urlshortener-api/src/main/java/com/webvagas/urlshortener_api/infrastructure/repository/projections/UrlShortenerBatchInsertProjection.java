package com.webvagas.urlshortener_api.infrastructure.repository.projections;

public interface UrlShortenerBatchInsertProjection {
    String getCode();

    String getOriginalUrl();
}
