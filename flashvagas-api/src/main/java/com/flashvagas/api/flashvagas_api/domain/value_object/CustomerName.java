package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CustomerName {
    private String value;

    public CustomerName(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or blank");
        }
        this.value = value;
    }

    @JsonCreator
    public static CustomerName from(String value) {
        return new CustomerName(value);
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
        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
