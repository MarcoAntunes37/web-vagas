package com.webvagas.urlshortener_api.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OriginalUrl {
    private String value;

    public OriginalUrl(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("OriginalUrl cannot be null or blank");
        }
        this.value = value;
    }

    @JsonCreator
    public static OriginalUrl from(String value) {
        return new OriginalUrl(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof OriginalUrl))
            return false;

        OriginalUrl originalUrl = (OriginalUrl) o;

        return value.equals(originalUrl.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
