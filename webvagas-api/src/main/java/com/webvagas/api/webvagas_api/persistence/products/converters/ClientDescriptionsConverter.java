package com.webvagas.api.webvagas_api.persistence.products.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webvagas.api.webvagas_api.domain.value_object.ClientDescriptions;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClientDescriptionsConverter implements AttributeConverter<ClientDescriptions, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ClientDescriptions attribute) {
        try {
            return attribute != null ? objectMapper.writeValueAsString(attribute.getItems()) : null;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao serializar ClientDescriptions", e);
        }
    }

    @Override
    public ClientDescriptions convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? new ClientDescriptions(dbData) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao desserializar ClientDescriptions", e);
        }
    }
}
