package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.enums.EmploymentType;

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
        return String.join(",", values.stream().map(Enum::name).toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return Objects.equals(values, this.values);
    }

    public int hashCode() {
        return Objects.hashCode(values);
    }
}
