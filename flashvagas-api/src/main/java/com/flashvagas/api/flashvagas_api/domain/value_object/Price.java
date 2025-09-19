package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Price {
    private String value;

    public Price(String value) {
        Objects.requireNonNull(value, "Price cannot be null.");

        if (value.toString().isBlank()) {
            throw new IllegalArgumentException("Price cannot be blank.");
        }

        if (!value.startsWith("price_")) {
            throw new IllegalArgumentException("Price must start with price_.");
        }

        this.value = value;
    }

    @JsonCreator
    public static Price from(String value) {
        return new Price(value);
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
        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}