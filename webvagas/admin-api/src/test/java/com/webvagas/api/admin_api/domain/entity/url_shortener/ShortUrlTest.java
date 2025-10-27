package com.webvagas.api.admin_api.domain.entity.url_shortener;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.webvagas.api.admin_api.domain.value_object.Code;
import com.webvagas.api.admin_api.domain.value_object.DateTime;
import com.webvagas.api.admin_api.domain.value_object.OriginalUrl;
import com.webvagas.api.admin_api.domain.value_object.ShortUrlId;

public class ShortUrlTest {
    private ShortUrlId id;
    private Code code;
    private OriginalUrl originalUrl;
    private DateTime createdAt;

    @BeforeEach
    public void Setup() {
        id = new ShortUrlId(UUID.randomUUID().toString());
        code = new Code("abc123");
        originalUrl = new OriginalUrl("https://www.google.com.br/");
        createdAt = new DateTime(LocalDateTime.now());
    }

    @Test
    public void shouldCreateShortUrlCorrectly() {
        ShortUrl shortUrl = new ShortUrl(id, code, originalUrl, createdAt);

        assertThat(shortUrl.getId()).isEqualTo(id);
        assertThat(shortUrl.getCode()).isEqualTo(code);
        assertThat(shortUrl.getOriginalUrl()).isEqualTo(originalUrl);
        assertThat(shortUrl.getCreatedAt()).isEqualTo(createdAt);
    }
}