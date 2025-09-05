package com.flashvagas.urlshortener_api.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ShortUrlId {
    private Long value;

    public ShortUrlId(Long value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("ShortUrlId cannot be null or blank");
        }
        this.value = value;
    }

    @JsonCreator
    public static ShortUrlId from(Long value) {
        return new ShortUrlId(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ShortUrlId))
            return false;

        ShortUrlId shortUrlId = (ShortUrlId) o;

        return value.equals(shortUrlId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
