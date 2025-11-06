package com.webvagas.urlshortener_api.domain.entity.short_url;

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

    public ShortUrlId getId() {
        return id;
    }

    public Code getCode() {
        return code;
    }

    public OriginalUrl getOriginalUrl() {
        return originalUrl;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getCreatedAtAsString() {
        return createdAt;
    }
}
