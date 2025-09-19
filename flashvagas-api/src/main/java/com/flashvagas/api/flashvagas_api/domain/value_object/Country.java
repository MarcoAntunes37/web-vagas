package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Country {
    private String value;

    public Country(String value) {
        Objects.requireNonNull(value, "Country cannot be null.");

        if (value.length() != 2)
            throw new IllegalArgumentException("Country must have 2 characters.");

        if (value.isBlank())
            throw new IllegalArgumentException("Country cannot be empty.");

        Boolean isValid = Arrays.stream(Locale.getAvailableLocales())
                .anyMatch(c -> c.getCountry().equalsIgnoreCase(value));

        if (!isValid)
            throw new IllegalArgumentException("Country must be a valid country code.");

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
        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @JsonCreator
    public static Country from(String value) {
        return new Country(value);
    }
}