package com.flashvagas.api.flashvagas_api.persistence.user_preferences.converters;

import com.flashvagas.api.flashvagas_api.domain.value_object.Keywords;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class KeywordsConverter implements AttributeConverter<Keywords, String> {

    @Override
    public String convertToDatabaseColumn(Keywords attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public Keywords convertToEntityAttribute(String dbData) {
        return dbData != null ? new Keywords(dbData) : null;
    }
}
