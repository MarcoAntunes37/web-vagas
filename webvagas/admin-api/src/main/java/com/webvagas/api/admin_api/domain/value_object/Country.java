package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class Country {
    private String value;

    public Country(String value) {
        Objects.requireNonNull(value, "Country cannot be null.");

        if (value.length() != 2)
            throw new IllegalArgumentException("Country must have 2 characters.");
            
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Country))
            return false;

        Country that = (Country) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}