package com.webvagas.urlshortener_api.domain.value_objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class CodeTest {
    @Test
    void shouldNotCreateCodeWithNullValue() {
        assertThatThrownBy(() -> new Code(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Code cannot be null.");
    }

    @Test
    void shouldNotCreateCodeWithEmptyValue() {
        assertThatThrownBy(() -> new Code(""))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Code cannot be empty.");
    }

    @Test
    void shouldCreateCode() {
        String codeString = UUID.randomUUID().toString();

        Code code = new Code(codeString);

        assertThat(code.getValue()).isEqualTo(codeString);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        String codeString = UUID.randomUUID().toString();

        Code code1 = new Code(codeString);

        Code code2 = new Code(codeString);

        assertThat(code1.equals(code2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        String codeString1 = UUID.randomUUID().toString();

        String codeString2 = UUID.randomUUID().toString();

        Code code1 = new Code(codeString1);
        
        Code code2 = new Code(codeString2);

        assertThat(code1.equals(code2)).isFalse();
    }
}