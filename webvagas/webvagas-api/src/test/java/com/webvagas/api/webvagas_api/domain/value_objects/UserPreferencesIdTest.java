package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserPreferencesIdTest {

    @Test
    void shouldNotCreateUserPreferencesIdWithNullValue() {
        assertThatThrownBy(() -> new UserPreferencesId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("UserPreferencesId cannot be null.");
    }

    @Test
    void shouldCreateUserPreferencesId() {
        UUID uuid = UUID.randomUUID();

        UserPreferencesId userPreferencesId = new UserPreferencesId(uuid);

        assertThat(userPreferencesId.getValue()).isEqualTo(uuid);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        UUID uuid = UUID.randomUUID();

        UserPreferencesId userPreferencesId1 = new UserPreferencesId(uuid);

        UserPreferencesId userPreferencesId2 = new UserPreferencesId(uuid);

        assertThat(userPreferencesId1.equals(userPreferencesId2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        UUID uuid1 = UUID.randomUUID();
        
        UUID uuid2 = UUID.randomUUID();

        UserPreferencesId userPreferencesId1 = new UserPreferencesId(uuid1);

        UserPreferencesId userPreferencesId2 = new UserPreferencesId(uuid2);

        assertThat(userPreferencesId1.equals(userPreferencesId2)).isFalse();
    }
}