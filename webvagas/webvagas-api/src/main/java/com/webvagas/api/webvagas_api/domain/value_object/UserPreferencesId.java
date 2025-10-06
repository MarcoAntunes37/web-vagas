package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;
import java.util.UUID;

public class UserPreferencesId {
    private UUID value;

    public UserPreferencesId(UUID value) {
        Objects.requireNonNull(value, "UserPreferencesId cannot be null.");

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

        UserPreferencesId that = (UserPreferencesId) o;

        return Objects.equals(that.value, this.value);
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