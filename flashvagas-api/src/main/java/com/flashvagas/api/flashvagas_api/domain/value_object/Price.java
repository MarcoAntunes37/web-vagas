package com.flashvagas.api.flashvagas_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Price {
    private String value;

    public Price(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("Price cannot be null or blank.");
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
        Price price = (Price) o;
        return value.equals(price.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}