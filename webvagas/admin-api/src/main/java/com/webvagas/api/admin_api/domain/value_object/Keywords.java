package com.webvagas.api.admin_api.domain.value_object;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Keywords {
    private final String value;

    public Keywords(String value) {
        Objects.requireNonNull(value, "Keywords cannot be null.");

        if (value.isBlank())
            throw new IllegalArgumentException("Keywords cannot be empty.");

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Keywords))
            return false;

        Keywords that = (Keywords) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}