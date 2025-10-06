package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.Keywords;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class KeywordsTest {

    @Test
    void shouldNotCreateKeywordsWithNullValue() {
        assertThatThrownBy(() -> new Keywords(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Keywords cannot be null.");
    }

    @Test
    void shouldNotCreateKeywordsWithLenghtGreaterThan255() {
        assertThatThrownBy(() -> new Keywords("a".repeat(256)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Keywords cannot be longer than 255 characters.");
    }

    @Test
    void shouldNotCreateKeywordsWhenRegexDoesNotMatch() {
        assertThatThrownBy(() -> new Keywords("abc!@#$%^&*()_+={}[]|\\:;\"'<>/?"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(
                        "Keywords cannot contain any of these special characters [!@#$%^&*()_+={}[]|\\:;\"'<>/?].");
    }

    @Test
    void shouldCreateKeywords() {
        Keywords keywords = new Keywords("abc");

        assertThat(keywords.getValue()).isEqualTo("abc");
        assertThat(keywords.getTerms()).isEqualTo(Arrays.asList("abc"));
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Keywords keywords1 = new Keywords("abc");
        Keywords keywords2 = new Keywords("abc");

        assertThat(keywords1.equals(keywords2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Keywords keywords1 = new Keywords("abc");
        Keywords keywords2 = new Keywords("def");

        assertThat(keywords1.equals(keywords2)).isFalse();
    }
}