package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

public class StripeCustomerPortalReturnUrl {
    private String value;

    public StripeCustomerPortalReturnUrl(String value) {
        Objects.requireNonNull(value, "StripeCustomerId cannot be null");
        
        if (value.toString().isBlank())
            throw new IllegalArgumentException("StripeCustomerId cannot be null or blank");

        if (!value.startsWith("https://"))
            throw new IllegalArgumentException("StripeCustomerPortalReturnUrl must start with https://");

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof StripeCustomerPortalReturnUrl))
            return false;

        return Objects.equals(value, this.value);
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
