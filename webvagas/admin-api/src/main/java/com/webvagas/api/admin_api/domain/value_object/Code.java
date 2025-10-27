package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class Code {
    private String value;

    public Code(String value) {
        Objects.requireNonNull(value, "Code cannot be null.");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Code))
            return false;

        Code that = (Code) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}