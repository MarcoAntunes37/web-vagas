package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientDescriptions {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final String value;

    public ClientDescriptions(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Client descriptions is required");

        this.value = value;
    }

    public static ClientDescriptions fromItems(List<ClientDescriptionItem> items) {
        try {
            String json = objectMapper.writeValueAsString(items);

            return new ClientDescriptions(json);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize ClientDescriptionItem list", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ClientDescriptions))
            return false;

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public List<ClientDescriptionItem> getItems() {
        try {
            List<ClientDescriptionItem> items = objectMapper.readValue(value, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, ClientDescriptionItem.class));

            return items;

        } catch (Exception e) {
            throw new IllegalStateException("Invalid clientDescriptions JSON format", e);
        }
    }
}