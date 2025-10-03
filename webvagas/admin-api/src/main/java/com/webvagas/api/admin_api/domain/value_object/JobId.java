package com.webvagas.api.admin_api.domain.value_object;

import com.fasterxml.jackson.annotation.JsonCreator;

public class JobId {
    private String value;

    public JobId(String value) {
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("JobId cannot be null or blank");
        }
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

        JobId jobId = (JobId) o;

        return value.equals(jobId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
