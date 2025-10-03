package com.webvagas.urlshortener_api.domain.value_objects;

import java.time.LocalDateTime;

public class DateTime {
    private LocalDateTime value;

    public DateTime(LocalDateTime value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("DateTime cannot be null or blank");
        }

        this.value = value;
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
