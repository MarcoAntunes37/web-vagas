package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductId {
    private String value;

    public ProductId(String value) {
        Objects.requireNonNull(value, "ProductId cannot be null");
        
        if (value.toString().isBlank())
            throw new IllegalArgumentException("ProductId cannot be blank");
            
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

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}