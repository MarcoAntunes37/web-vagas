package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;


public class CustomerEmail {
    private String value;
    private String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public CustomerEmail(String value) {
        Objects.requireNonNull(value, "Customer email cannot be null.");

        if (value.toString().isBlank()) {
            throw new IllegalArgumentException("Customer email cannot be empty.");
        }

        if (!value.matches(regex)) {
            throw new IllegalArgumentException("Customer email must be a valid email address.");
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

        if (!(o instanceof CustomerEmail))
            return false;

        CustomerEmail that = (CustomerEmail) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}