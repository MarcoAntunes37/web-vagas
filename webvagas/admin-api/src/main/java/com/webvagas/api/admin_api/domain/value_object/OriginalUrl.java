package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class OriginalUrl {
    private String value;

    public OriginalUrl(String value) {
        Objects.requireNonNull(value, "OriginalUrl cannot be null.");
        if (value.isBlank()) {
            throw new IllegalArgumentException("OriginalUrl cannot be empty.");
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

        if (!(o instanceof OriginalUrl))
            return false;

        OriginalUrl that = (OriginalUrl) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}