package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class KeywordsTest {
    private String value;

    @BeforeEach
    void setUp() {
        value = "keywords";
    }

    @Test
    void shouldFailToCreateWhenWhenKeywordsIsNull() {
        value = null;

        assertThatThrownBy(() -> new Keywords(value))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Keywords cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenKeywordsIsEmpty() {
        value = "";

        assertThatThrownBy(() -> new Keywords(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Keywords cannot be empty.");
    }

    @Test
    void shouldCreateKeywords() {
        Keywords keywords = new Keywords(value);

        assertThat(keywords).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Keywords keywords = new Keywords(value);

        value = "keywords2";

        Keywords keywords2 = new Keywords(value);

        assertThat(keywords.equals(keywords2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Keywords keywords = new Keywords(value);

        Keywords keywords2 = new Keywords(value);

        assertThat(keywords.equals(keywords2)).isTrue();
    }
}