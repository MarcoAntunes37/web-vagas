package com.webvagas.api.webvagas_api.domain.value_object;

import java.util.Objects;

public class JobId {
    private String value;

    public JobId(String value) {
        Objects.requireNonNull(value, "JobId cannot be null.");

        if (value.toString().isBlank())
            throw new IllegalArgumentException("JobId cannot be empty.");

        this.value = value;
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

        JobId that = (JobId) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
