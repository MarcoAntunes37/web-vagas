package com.webvagas.urlshortener_api.domain.value_objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class OriginalUrlTest {
    @Test
    void shouldNotCreateOriginalUrlWithNullValue() {
        assertThatThrownBy(() -> new OriginalUrl(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotCreateOriginalUrlWithEmptyValue() {
        assertThatThrownBy(() -> new OriginalUrl(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldCreateOriginalUrl() {
        String urlString = "https://www.google.com.br/";

        OriginalUrl originalUrl = new OriginalUrl(urlString);

        assertThat(originalUrl.getValue()).isEqualTo(urlString);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        String value = "https://www.google.com.br/";

        OriginalUrl originalUrl1 = new OriginalUrl(value);

        OriginalUrl originalUrl2 = new OriginalUrl(value);

        assertThat(originalUrl1.equals(originalUrl2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        String value1 = "https://www.google.com.br/";

        String value2 = "https://www.google.com.br/2";

        OriginalUrl originalUrl1 = new OriginalUrl(value1);

        OriginalUrl originalUrl2 = new OriginalUrl(value2);

        assertThat(originalUrl1.equals(originalUrl2)).isFalse();
    }
}
