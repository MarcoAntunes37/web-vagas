package com.flashvagas.api.flashvagas_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Country {
    private String value;

    public Country(String value) {        
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