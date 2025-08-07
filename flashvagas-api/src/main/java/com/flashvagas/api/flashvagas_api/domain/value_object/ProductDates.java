package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductDates {
    private OffsetDateTime created;
    private OffsetDateTime updated;

    public ProductDates(OffsetDateTime created, OffsetDateTime updated) {
        this.created = created;
        this.updated = updated;
    }

    @JsonCreator
    public static ProductDates toProductDates(OffsetDateTime created, OffsetDateTime updated) {
        return new ProductDates(created, updated);
    }

    public ProductDates getValue() {
        return new ProductDates(created, updated);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ProductDates))
            return false;

        ProductDates productDates = (ProductDates) o;

        return created.equals(productDates.created) &&
                updated.equals(productDates.updated);
    }

    @Override
    public int hashCode() {
        int result = created.hashCode();
        result = 31 * result + updated.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductDates{" +
                "created=" + created +
                ", updated=" + updated +
                '}';
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }
}
