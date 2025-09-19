package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ProductDates {
    private OffsetDateTime created;
    private OffsetDateTime updated;

    public ProductDates(OffsetDateTime created, OffsetDateTime updated) {
        Objects.requireNonNull(created);

        Objects.requireNonNull(updated);

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

        return Objects.equals(created, this.created)
                && Objects.equals(updated, this.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, updated);
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
