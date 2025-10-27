package com.webvagas.api.admin_api.domain.value_object;

import java.time.LocalDateTime;
import java.util.Objects;

public class DateTime {
    private LocalDateTime value;

    public DateTime(LocalDateTime value) {
        Objects.requireNonNull(value, "DateTime cannot be null.");

        this.value = value;
    }

    public String getStringValue() {
        return value.toString();
    }

    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof DateTime))
            return false;

        DateTime that = (DateTime) o;

        return Objects.equals(that.value, this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}