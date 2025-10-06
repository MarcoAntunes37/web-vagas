package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;

public record EmploymentTypes(Set<EmploymentType> values) {
    public EmploymentTypes {
        if (values == null || values.isEmpty()) {
            values = Collections.emptySet();
        }
    }

    public boolean contains(EmploymentType type) {
        return values.contains(type);
    }

    @Override
    public String toString() {
        return String.join(",", getSortedTypes().stream().map(Enum::name).toList());

    }

    private Collection<EmploymentType> getSortedTypes() {
        List<EmploymentType> types = values.stream().toList();

        return types.stream().sorted().toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EmploymentTypes that = (EmploymentTypes) o;

        return Objects.equals(that.values, this.values);
    }

    public int hashCode() {
        return Objects.hashCode(values);
    }
}