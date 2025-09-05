package com.flashvagas.urlshortener_api.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Code {
    private String value;

    public Code(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("Code cannot be null or blank");
        }
        this.value = value;
    }

    @JsonCreator
    public static Code from(String value) {
        return new Code(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Code))
            return false;

        Code code = (Code) o;

        return value.equals(code.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
