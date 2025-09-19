package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductPrices {
    private final String defaultPrice;
    private final Double clientPrice;

    public ProductPrices(String defaultPrice, double clientPrice) {
        Objects.requireNonNull(defaultPrice, "Default price cannot be null.");

        Objects.requireNonNull(clientPrice, "Client price cannot be null.");
        
        if (clientPrice < 0)
            throw new IllegalArgumentException("Client price can't be negative.");

        if (defaultPrice.toString().isBlank())
            throw new IllegalArgumentException("Default price cannot be null or blank.");

        if (clientPrice == 0)
            throw new IllegalArgumentException("Client price can't be zero.");

        this.defaultPrice = defaultPrice;
        this.clientPrice = clientPrice;
    }

    @JsonCreator
    public static ProductPrices toProductPricese(String defaultPrice, double clientPrice) {
        return new ProductPrices(defaultPrice, clientPrice);
    }

    public ProductPrices getValue() {
        return new ProductPrices(defaultPrice, clientPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ProductPrices))
            return false;

        return Objects.equals(defaultPrice, this.defaultPrice)
                && Objects.equals(clientPrice, this.clientPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultPrice, clientPrice);
    }

    @Override
    public String toString() {
        return "ProductPrices{" +
                "defaultPrice='" + defaultPrice + '\'' +
                ", clientPrice=" + clientPrice +
                '}';
    }

    public String getDefaultPrice() {
        return defaultPrice;
    }

    public double getClientPrice() {
        return clientPrice;
    }
}
