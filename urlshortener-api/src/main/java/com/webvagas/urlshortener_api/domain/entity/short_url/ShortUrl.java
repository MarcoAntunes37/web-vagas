package com.webvagas.urlshortener_api.domain.entity.short_url;

import java.time.LocalDateTime;

import com.webvagas.urlshortener_api.domain.value_objects.Code;
import com.webvagas.urlshortener_api.domain.value_objects.DateTime;
import com.webvagas.urlshortener_api.domain.value_objects.OriginalUrl;
import com.webvagas.urlshortener_api.domain.value_objects.ShortUrlId;

public class ShortUrl {
    private ShortUrlId id;
    private Code code;
    private OriginalUrl originalUrl;
    private DateTime createdAt;

    public ShortUrl(
            ShortUrlId id,
            Code code,
            OriginalUrl originalUrl,
            DateTime createdAt) {
        this.id = id;
        this.code = code;
        this.originalUrl = originalUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id.getValue();
    }

    public String getCode() {
        return code.getValue();
    }

    public String getOriginalUrl() {
        return originalUrl.getValue();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.getValue();
    }

    public String getCreatedAtAsString() {
        return createdAt.getStringValue();
    }
}
