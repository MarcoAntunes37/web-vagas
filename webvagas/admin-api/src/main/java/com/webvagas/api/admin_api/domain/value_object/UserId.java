package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private UUID value;

    public UserId(UUID value) {
        Objects.requireNonNull(value, "UserId cannot be null.");

        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof UserId))
            return false;

        UserId that = (UserId) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}