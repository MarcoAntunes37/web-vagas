package com.flashvagas.api.admin_api.domain.value_object;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class UserId {
    private UUID value;

    public UserId(UUID value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or blank");
        }

        this.value = value;
    }

    @JsonCreator
    public static UserId fromString(String value) {
        return new UserId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return value.toString();
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
        UserId userId = (UserId) o;
        return value.equals(userId.value);
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