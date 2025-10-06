package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

public class StripeCustomerId {
    private String value;

    public StripeCustomerId(String value) {
        Objects.requireNonNull(value, "StripeCustomerId cannot be null.");

        if (value.toString().isBlank())
            throw new IllegalArgumentException("StripeCustomerId cannot be empty.");
            
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof StripeCustomerId))
            return false;

        StripeCustomerId that = (StripeCustomerId) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}