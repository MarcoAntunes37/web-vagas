package com.flashvagas.api.flashvagas_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductId {
    private String value;

    public ProductId(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("ProductId cannot be null or blank");
        }
        this.value = value;
    }

    @JsonCreator
    public static ProductId fromString(String value) {
        return new ProductId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ProductId))
            return false;

        ProductId productId = (ProductId) o;

        return value.equals(productId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}