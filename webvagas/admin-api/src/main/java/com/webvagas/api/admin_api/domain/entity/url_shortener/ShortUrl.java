package com.webvagas.api.admin_api.domain.entity.url_shortener;

import com.webvagas.api.admin_api.domain.value_object.Code;
import com.webvagas.api.admin_api.domain.value_object.DateTime;
import com.webvagas.api.admin_api.domain.value_object.OriginalUrl;
import com.webvagas.api.admin_api.domain.value_object.ShortUrlId;

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
}