package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class Publisher {
    private String name;

    public Publisher(String name) {
        Objects.requireNonNull(name, "Name cannot be null.");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Publisher))
            return false;

        Publisher that = (Publisher) o;

        return Objects.equals(that.name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}