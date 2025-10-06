package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

public class CustomerName {
    private String value;

    public CustomerName(String value) {
        Objects.requireNonNull(value, "Customer name cannot be null.");

        if (value.toString().isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
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

        if (!(o instanceof CustomerName))
            return false;

        CustomerName that = (CustomerName) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
