package com.flashvagas.urlshortener_api.domain.value_objects;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DateTime {
    private LocalDateTime value;

    public DateTime(LocalDateTime value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("DateTime cannot be null or blank");
        }

        this.value = value;
    }

    @JsonCreator
    public static Code from(String value) {
        return new Code(value);
    }

    public String getStringValue() {
        return value.toString();
    }

    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof DateTime))
            return false;

        DateTime localDateTime = (DateTime) o;

        return value.equals(localDateTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
