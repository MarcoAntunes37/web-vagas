package com.webvagas.urlshortener_api.persistence.repository.projections;

public interface ShortUrlBatchInsertProjection {
    String getCode();

    String getOriginalUrl();
}
