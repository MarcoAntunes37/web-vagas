package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.List;

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

        ClientDescriptions other = (ClientDescriptions) o;

        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
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