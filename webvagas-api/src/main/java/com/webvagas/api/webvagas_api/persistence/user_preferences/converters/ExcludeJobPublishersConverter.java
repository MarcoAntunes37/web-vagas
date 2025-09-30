package com.webvagas.api.webvagas_api.persistence.user_preferences.converters;

import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ExcludeJobPublishersConverter implements AttributeConverter<ExcludeJobPublishers, String> {

    @Override
    public String convertToDatabaseColumn(ExcludeJobPublishers attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public ExcludeJobPublishers convertToEntityAttribute(String dbData) {
        return dbData != null ? new ExcludeJobPublishers(dbData) : null;
    }
}
