package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExcludeJobPublishers {
    private final String value;

    public ExcludeJobPublishers(String value) {
        Objects.requireNonNull(value, "ExcludeJobPublishers cannot be null.");

        this.value = value;
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

    @JsonCreator
    public static ExcludeJobPublishers toExcludeJobPublishers(String value) {
        return new ExcludeJobPublishers(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ExcludeJobPublishers))
            return false;

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}