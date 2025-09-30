package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CustomerEmail {
    private String value;
    private String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public CustomerEmail(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("Customer email cannot be null or blank");
        }

        if (!value.matches(regex)) {
            throw new IllegalArgumentException("Customer email must be a valid email address");
        }

        this.value = value;
    }

    @JsonCreator
    public static CustomerEmail from(String value) {
        return new CustomerEmail(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CustomerEmail))
            return false;

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}