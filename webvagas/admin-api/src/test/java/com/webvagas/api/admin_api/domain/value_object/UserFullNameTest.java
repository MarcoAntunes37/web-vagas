package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFullNameTest {
    private String firstName;

    private String lastName;

    @BeforeEach
    void setup() {
        firstName = "firstname";
        lastName = "lastname";
    }

    @Test
    void shouldFailToCreateWhenWhenFirstNameIsNull() {
        firstName = null;

        assertThatThrownBy(() -> new UserFullName(firstName, lastName))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("First name cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenLastNameIsNull() {
        lastName = null;

        assertThatThrownBy(() -> new UserFullName(firstName, lastName))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Last name cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenFirstNameIsEmpty() {
        firstName = "";

        assertThatThrownBy(() -> new UserFullName(firstName, lastName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("First name cannot be empty.");
    }

    @Test
    void shouldFailToCreateWhenWhenLastNameIsEmpty() {
        lastName = "";

        assertThatThrownBy(() -> new UserFullName(firstName, lastName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Last name cannot be empty.");
    }

    @Test
    void shouldCreateUserFullName() {
        UserFullName userFullName = new UserFullName(firstName, lastName);

        assertThat(userFullName.getFirstName()).isEqualTo(firstName);
        assertThat(userFullName.getLastName()).isEqualTo(lastName);
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        UserFullName userFullName = new UserFullName(firstName, lastName);

        firstName = "firstname2";
        lastName = "lastname2";

        UserFullName userFullName2 = new UserFullName(firstName, lastName);

        assertThat(userFullName.equals(userFullName2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        UserFullName userFullName = new UserFullName(firstName, lastName);

        UserFullName userFullName2 = new UserFullName(firstName, lastName);

        assertThat(userFullName.equals(userFullName2)).isTrue();
    }
}