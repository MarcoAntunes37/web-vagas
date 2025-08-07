package com.flashvagas.api.flashvagas_api.domain.value_object;

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
        CustomerName customerName = (CustomerName) o;
        return value.equals(customerName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
