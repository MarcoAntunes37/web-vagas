package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Keywords {
    private final String value;

    public Keywords(String value) {
        Objects.requireNonNull(value, "Keywords cannot be null");

        if (value.length() > 255)
            throw new IllegalArgumentException("Keywords cannot be longer than 255 characters");

        if (value.contains("!@#$%^&*()_+={}[]|\\:;\"'<>/?"))
            throw new IllegalArgumentException("Keywords cannot contain any of these special characters [!@#$%^&*()_+={}[]|\\:;\"'<>/?]");

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

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}