package com.flashvagas.api.flashvagas_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductPrices {
    private final String defaultPrice;
    private final double clientPrice;

    public ProductPrices(String defaultPrice, double clientPrice) {
        if (clientPrice < 0)
            throw new IllegalArgumentException("Client price can't be negative.");

        if (defaultPrice == null || defaultPrice.toString().isBlank())
            throw new IllegalArgumentException("Default price cannot be null or blank.");

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

        ProductPrices productPrice = (ProductPrices) o;

        return defaultPrice.equals(productPrice.defaultPrice) &&
                clientPrice == productPrice.clientPrice;
    }

    @Override
    public int hashCode() {
        int result = defaultPrice.hashCode();
        result = 31 * result + (int) clientPrice;
        return result;
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
