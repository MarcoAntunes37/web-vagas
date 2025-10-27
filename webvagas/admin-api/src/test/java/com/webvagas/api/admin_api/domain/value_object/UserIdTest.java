package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIdTest {
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
    }

    @Test
    void shouldFailToCreateWhenWhenUsernameIsNull() {
        id = null;
        assertThatThrownBy(() -> new UserId(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("UserId cannot be null.");
    }

    @Test
    void shouldCreateUserId() {
        UserId userId = new UserId(id);

        assertThat(userId.getValue()).isEqualTo(id);
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        UserId userId1 = new UserId(id);

        id = UUID.randomUUID();

        UserId userId2 = new UserId(id);

        assertThat(userId1.equals(userId2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        UserId userId1 = new UserId(id);

        UserId userId2 = new UserId(id);

        assertThat(userId1.equals(userId2)).isTrue();
    }
}