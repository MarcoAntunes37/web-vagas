package com.webvagas.api.admin_api.domain.value_object;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExcludeJobPublishers {
    private final String value;

    public ExcludeJobPublishers(String value) {
        if (value == null)
            throw new IllegalArgumentException("ExcludeJobPublishers is required in this type of search");

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

        ExcludeJobPublishers excludeJobPublishers = (ExcludeJobPublishers) o;

        return value.equals(excludeJobPublishers.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}