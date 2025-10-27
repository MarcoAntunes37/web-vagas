package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class ShortUrlId {
    private String value;

    public ShortUrlId(String value) {
        Objects.requireNonNull(value, "ShortUrlId cannot be null.");
            
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ShortUrlId))
            return false;

        ShortUrlId that = (ShortUrlId) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}