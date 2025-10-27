package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PostedTest {
    private String humanReadable;
    private Timestamp timestamp;
    private OffsetDateTime dateTimeutc;

    @BeforeEach
    void setUp() {
        humanReadable = "Human readable";
        timestamp = new Timestamp(System.currentTimeMillis());
        dateTimeutc = OffsetDateTime.now();
    }

    @Test
    void shouldFailToCreateWhenWhenHumanReadableIsNull() {
        humanReadable = null;

        assertThatThrownBy(() -> new Posted(humanReadable, timestamp, dateTimeutc))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Human readable cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenTimestampIsNull() {
        timestamp = null;

        assertThatThrownBy(() -> new Posted(humanReadable, timestamp, dateTimeutc))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Timestamp cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenDateTimeUtcIsNull() {
        dateTimeutc = null;

        assertThatThrownBy(() -> new Posted(humanReadable, timestamp, dateTimeutc))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Date time utc cannot be null.");
    }

    @Test
    void shouldCreatePosted() {
        Posted posted = new Posted(humanReadable, timestamp, dateTimeutc);

        assertThat(posted).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Posted posted = new Posted(humanReadable, timestamp, dateTimeutc);

        humanReadable = "Human readable2";
        timestamp = new Timestamp(System.currentTimeMillis());
        dateTimeutc = OffsetDateTime.now();

        Posted posted2 = new Posted(humanReadable, timestamp, dateTimeutc);

        assertThat(posted.equals(posted2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Posted posted = new Posted(humanReadable, timestamp, dateTimeutc);

        Posted posted2 = new Posted(humanReadable, timestamp, dateTimeutc);

        assertThat(posted.equals(posted2)).isTrue();
    }
}