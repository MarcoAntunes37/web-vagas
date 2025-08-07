package com.flashvagas.api.admin_api.domain.value_object;

import java.util.Collections;
import java.util.Set;

import com.flashvagas.api.admin_api.domain.entity.user_preferences.enums.EmploymentType;

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
}
