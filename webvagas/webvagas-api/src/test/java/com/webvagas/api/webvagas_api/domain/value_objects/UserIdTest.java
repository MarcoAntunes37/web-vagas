package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.UserId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

public class UserIdTest {
        @Test
        void shouldNotCreateUserIdWithNullValue() {
                assertThatThrownBy(() -> new UserId(null))
                                .isInstanceOf(NullPointerException.class)
                                .hasMessage("UserId cannot be null.");
        }

        @Test
        void shouldCreateUserId() {
                UUID userId = UUID.randomUUID();

                UserId id = new UserId(userId);

                assertThat(id.getValue()).isEqualTo(userId);
        }

        @Test
        void shouldReturnTrueWhenEquals() {
                UUID userId = UUID.randomUUID();

                UserId id1 = new UserId(userId);

                UserId id2 = new UserId(userId);

                assertThat(id1.equals(id2)).isTrue();
        }

        @Test
        void shouldReturnFalseWhenNotEquals() {
                UUID userId1 = UUID.randomUUID();

                UUID userId2 = UUID.randomUUID();

                UserId id1 = new UserId(userId1);

                UserId id2 = new UserId(userId2);

                assertThat(id1.equals(id2)).isFalse();
        }
}