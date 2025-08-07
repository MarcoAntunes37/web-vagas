package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.UUID;

public class UserPreferencesId {
    private UUID value;

    public UserPreferencesId(UUID value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("UserPreferencesId cannot be null or blank");
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

        UserPreferencesId userPreferencesId = (UserPreferencesId) o;

        return value.equals(userPreferencesId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
