package com.webvagas.api.admin_api.domain.value_object;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class ExcludeJobPublishers {
    private final Set<String> value;

    public ExcludeJobPublishers(Set<String> value) {
        Objects.requireNonNull(value, "ExcludeJobPublishers cannot be null.");

        if (value.isEmpty())
            value = Collections.emptySet();

        this.value = value;
    }

    public Set<String> getValue() {
        return value;
    }

    public Set<String> getTerms() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ExcludeJobPublishers))
            return false;

        ExcludeJobPublishers that = (ExcludeJobPublishers) o;

        return Objects.equals(that.value, this.value);
    }

    private Collection<String> getSortedExcludeJobPublishers(Set<String> excludeJobPublishers) {
        Collection<String> sortedExcludeJobPublishers = excludeJobPublishers.stream().toList();

        return sortedExcludeJobPublishers.stream().sorted().toList();
    }

    @Override
    public String toString() {
        return String.join(",", getSortedExcludeJobPublishers(value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}