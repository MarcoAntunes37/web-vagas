package com.flashvagas.api.flashvagas_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductDetails {
    private String name;
    private String description;
    private Boolean active;
    private ClientDescriptions clientDescription;

    public ProductDetails(String name,
                          String description,
                          Boolean active,
                          ClientDescriptions clientDescription) {

        if (name == null || name.toString().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }

        if (active == null) {
            throw new IllegalArgumentException("Active cannot be null.");
        }

        this.name = name;
        this.description = description;
        this.active = active;
        this.clientDescription = clientDescription;
    }

    @JsonCreator
    public static ProductDetails toProductDetails(String name, String description, Boolean active,
                                                  ClientDescriptions clientDescription) {
        return new ProductDetails(name, description, active, clientDescription);
    }

    public ProductDetails getValue() {
        return new ProductDetails(name, description, active, clientDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ProductDetails))
            return false;

        ProductDetails productDetails = (ProductDetails) o;

        return name.equals(productDetails.name) &&
                description.equals(productDetails.description) &&
                active.equals(productDetails.active) &&
                clientDescription.equals(productDetails.clientDescription);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + active.hashCode();
        result = 31 * result + clientDescription.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", clientDescription=" + clientDescription +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }

    public ClientDescriptions getClientDescriptions() {
        return clientDescription;
    }
}
