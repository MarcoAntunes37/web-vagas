package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.List;
import java.util.Objects;

public class ClientDescriptions {
    private final List<ClientDescriptionItem> items;

    public ClientDescriptions(List<ClientDescriptionItem> items) {
        Objects.requireNonNull(items, "Items cannot be null");

        if (items.isEmpty())
            throw new IllegalArgumentException("Items cannot be empty");

        this.items = List.copyOf(items);
    }

    public List<ClientDescriptionItem> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ClientDescriptions))
            return false;
        ClientDescriptions that = (ClientDescriptions) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
