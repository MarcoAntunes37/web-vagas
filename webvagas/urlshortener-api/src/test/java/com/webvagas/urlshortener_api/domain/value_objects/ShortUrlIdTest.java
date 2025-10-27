package com.webvagas.urlshortener_api.domain.value_objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class ShortUrlIdTest {
    @Test
    void shoudlNotCreateShortUrlIdWithNullValue() {
        assertThatThrownBy(() -> new ShortUrlId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("ShortUrlId cannot be null.");
    }

    @Test
    void shouldCreateShortUrlId() {
        Long value = 1L;

        ShortUrlId shortUrlId = new ShortUrlId(value);

        assertThat(shortUrlId.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        ShortUrlId shortUrlId1 = new ShortUrlId(1L);

        ShortUrlId shortUrlId2 = new ShortUrlId(1L);

        assertThat(shortUrlId1.equals(shortUrlId2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        ShortUrlId shortUrlId1 = new ShortUrlId(1L);

        ShortUrlId shortUrlId2 = new ShortUrlId(2L);

        assertThat(shortUrlId1.equals(shortUrlId2)).isFalse();
    }
}