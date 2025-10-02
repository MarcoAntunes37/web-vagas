package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ExcludeJobPublishers {
    private final Set<String> value;

    public ExcludeJobPublishers(Set<String> value) {
        Objects.requireNonNull(value, "ExcludeJobPublishers cannot be null.");

        this.value = value;
    }

    public Set<String> getValue() {
        return value;
    }

    public Set<String> getTerms() {
        return value;
    }

    @JsonCreator
    public static ExcludeJobPublishers toExcludeJobPublishers(Set<String> value) {
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
    public String toString() {
        return String.join(",", value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}