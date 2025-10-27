package com.webvagas.api.admin_api.domain.value_object;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.webvagas.api.admin_api.domain.entity.job.enums.EmploymentType;


public class JobEmploymentTypes {
    private Set<EmploymentType> values;

    public JobEmploymentTypes(Set<EmploymentType> values) {
        Objects.requireNonNull(values, "Job employment types cannot be null.");

        if (values.isEmpty()) {
            values = Collections.emptySet();
        }

        this.values = values;
    }

    public boolean contains(EmploymentType type) {
        return values.contains(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof JobEmploymentTypes))
            return false;

        JobEmploymentTypes that = (JobEmploymentTypes) o;

        return Objects.equals(that.values, this.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}