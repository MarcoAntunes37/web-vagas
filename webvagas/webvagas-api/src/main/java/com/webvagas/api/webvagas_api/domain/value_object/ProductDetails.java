package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

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
                Objects.requireNonNull(name);
                Objects.requireNonNull(description);
                Objects.requireNonNull(active);
                Objects.requireNonNull(clientDescription);

        if (name.toString().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
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

        return Objects.equals(name, this.name)
                && Objects.equals(description, this.description)
                && Objects.equals(active, this.active)
                && Objects.equals(clientDescription, this.clientDescription);
    }

    @Override
    public int hashCode() {        
        return Objects.hash(name, description, active, clientDescription);
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
