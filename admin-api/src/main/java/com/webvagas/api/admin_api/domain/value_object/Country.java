package com.webvagas.api.admin_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Country {
    private String value;

    public Country(String value) {
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
        Country country = (Country) o;
        return value.equals(country.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @JsonCreator
    public static Country from(String value) {
        return new Country(value);
    }
}