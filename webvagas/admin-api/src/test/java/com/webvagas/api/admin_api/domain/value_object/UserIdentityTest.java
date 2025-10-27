package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class UserIdentityTest {
    private String username;

    private String email;

    @BeforeEach
    void setUp() {
        username = "username";
        email = "email";
    }

    @Test
    void shouldFailToCreateWhenEmailIsNull() {
        email = null;

        assertThatThrownBy(() -> new UserIdentity(username, email))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Email cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenUsernameIsNull() {
        username = null;

        assertThatThrownBy(() -> new UserIdentity(username, email))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Username cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenEmailIsEmpty() {
        email = "";

        assertThatThrownBy(() -> new UserIdentity(username, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email cannot be empty.");
    }

    @Test
    void shouldFailToCreateWhenWhenUsernameIsEmpty() {
        username = "";

        assertThatThrownBy(() -> new UserIdentity(username, email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be empty.");
    }

    @Test
    void shouldCreateUserIdentity() {
        UserIdentity userIdentity = new UserIdentity(username, email);

        assertThat(userIdentity.getUsername()).isEqualTo("username");
        assertThat(userIdentity.getEmail()).isEqualTo("email");
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        UserIdentity userIdentity1 = new UserIdentity(username, email);

        email = "email2";
        username = "username2";

        UserIdentity userIdentity2 = new UserIdentity(username, email);

        assertThat(userIdentity1.equals(userIdentity2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        UserIdentity userIdentity1 = new UserIdentity(username, email);
        UserIdentity userIdentity2 = new UserIdentity(username, email);

        assertThat(userIdentity1.equals(userIdentity2)).isTrue();
    }
}