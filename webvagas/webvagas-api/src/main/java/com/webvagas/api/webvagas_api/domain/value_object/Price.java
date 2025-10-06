package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

public class Price {
    private String value;

    public Price(String value) {
        Objects.requireNonNull(value, "Price cannot be null.");

        if (value.toString().isBlank()) {
            throw new IllegalArgumentException("Price cannot be empty.");
        }

        if (!value.startsWith("price_")) {
            throw new IllegalArgumentException("Price must start with price_.");
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

        if (!(o instanceof Price))
            return false;

        Price that = (Price) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}