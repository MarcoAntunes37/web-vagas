package com.flashvagas.api.flashvagas_api.domain.value_object;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

public class JobId {
    private String value;

    public JobId(String value) {
        Objects.requireNonNull(value, "JobId cannot be null");
        if (value.toString().isBlank())
            throw new IllegalArgumentException("JobId cannot be blank");

        this.value = value;
    }

    @JsonCreator
    public static JobId from(String value) {
        return new JobId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof JobId))
            return false;

        return Objects.equals(value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
