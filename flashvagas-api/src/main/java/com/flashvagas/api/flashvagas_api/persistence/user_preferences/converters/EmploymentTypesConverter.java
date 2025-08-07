package com.flashvagas.api.flashvagas_api.persistence.user_preferences.converters;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.flashvagas.api.flashvagas_api.domain.value_object.EmploymentTypes;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EmploymentTypesConverter implements AttributeConverter<EmploymentTypes, String> {

    @Override
    public String convertToDatabaseColumn(EmploymentTypes attribute) {
        if (attribute == null || attribute.values().isEmpty()) return null;

        return attribute.values().stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public EmploymentTypes convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return new EmploymentTypes(Set.of());

        Set<EmploymentType> values = Arrays.stream(dbData.split(","))
                .map(String::trim)
                .map(EmploymentType::valueOf)
                .collect(Collectors.toSet());

        return new EmploymentTypes(values);
    }
}
