package com.flashvagas.api.flashvagas_api.domain.value_object;

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
        CustomerEmail customerEmail = (CustomerEmail) o;
        return value.equals(customerEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}