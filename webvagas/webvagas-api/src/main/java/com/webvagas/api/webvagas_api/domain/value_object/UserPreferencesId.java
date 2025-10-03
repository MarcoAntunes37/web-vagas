package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;
import java.util.UUID;

public class UserPreferencesId {
    private UUID value;

    public UserPreferencesId(UUID value) {
        Objects.requireNonNull(value, "UserPreferencesId cannot be null");

        if (value.toString().isBlank()) {
            throw new IllegalArgumentException("UserPreferencesId cannot be blank");
        }
        
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserPreferencesId))
            return false;

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
