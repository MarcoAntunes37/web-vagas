package com.webvagas.api.admin_api.domain.value_object;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Keywords {
    private final String value;

    public Keywords(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("keywords is required in this type of search");

        this.value = value;
    }

    @JsonCreator
    public static Keywords toKeywords(String value) {
        return new Keywords(value);
    }

    public String getValue() {
        return value;
    }

    public List<String> getTerms() {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Keywords))
            return false;

        Keywords keywords = (Keywords) o;

        return value.equals(keywords.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}