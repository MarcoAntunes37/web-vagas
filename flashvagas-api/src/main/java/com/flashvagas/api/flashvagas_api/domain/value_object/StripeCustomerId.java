package com.flashvagas.api.flashvagas_api.domain.value_object;

public class StripeCustomerId {
    private String value;

    public StripeCustomerId(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("StripeCustomerId cannot be null or blank");
        }
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

        StripeCustomerId stripeCustomerId = (StripeCustomerId) o;

        return value.equals(stripeCustomerId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
