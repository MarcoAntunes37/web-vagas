package com.webvagas.urlshortener_api.domain.entity.short_url;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import com.webvagas.urlshortener_api.domain.value_objects.Code;
import com.webvagas.urlshortener_api.domain.value_objects.DateTime;
import com.webvagas.urlshortener_api.domain.value_objects.OriginalUrl;
import com.webvagas.urlshortener_api.domain.value_objects.ShortUrlId;

public class ShortUrlTest {
    private ShortUrlId id;
    private Code code;
    private OriginalUrl originalUrl;
    private DateTime createdAt;

    @BeforeEach
    void Setup() {
        id = new ShortUrlId(1L);
        code = new Code("abc123");
        originalUrl = new OriginalUrl("https://www.google.com.br/");
        createdAt = new DateTime(LocalDateTime.now());
    }

    @Test
    void shouldCreateShortUrlCorrectly() {
        ShortUrl shortUrl = new ShortUrl(id, code, originalUrl, createdAt);

        assertThat(shortUrl.getId()).isEqualTo(id);
        assertThat(shortUrl.getCode()).isEqualTo(code);
        assertThat(shortUrl.getOriginalUrl()).isEqualTo(originalUrl);
        assertThat(shortUrl.getCreatedAt()).isEqualTo(createdAt);
    }
}
