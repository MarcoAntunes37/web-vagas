package com.webvagas.urlshortener_api.domain.value_objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
    @Test
    void shouldNotCreateDateTimeWithNullValue() {
        assertThatThrownBy(() -> new DateTime(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("DateTime cannot be null.");
    }

    @Test
    void shouldCreateDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();

        DateTime dateTime = new DateTime(localDateTime);

        assertThat(dateTime.getValue()).isEqualTo(localDateTime);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        LocalDateTime localDateTime = LocalDateTime.now();

        DateTime dateTime1 = new DateTime(localDateTime);

        DateTime dateTime2 = new DateTime(localDateTime);

        assertThat(dateTime1.equals(dateTime2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        LocalDateTime localDateTime = LocalDateTime.now();

        DateTime dateTime1 = new DateTime(localDateTime);

        DateTime dateTime2 = new DateTime(localDateTime.plusDays(1));

        assertThat(dateTime1.equals(dateTime2)).isFalse();
    }
}