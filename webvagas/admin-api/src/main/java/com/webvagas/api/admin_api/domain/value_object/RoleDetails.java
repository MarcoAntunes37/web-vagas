package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class RoleDetails {
    private String id;
    private String name;
    private String description;

    public RoleDetails(String id, String name, String description) {
        Objects.requireNonNull(id, "Id cannot be null.");

        Objects.requireNonNull(name, "Name cannot be null.");

        Objects.requireNonNull(description, "Description cannot be null.");

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof RoleDetails))
            return false;

        RoleDetails that = (RoleDetails) o;

        return Objects.equals(that.id, this.id)
                && Objects.equals(that.name, this.name)
                && Objects.equals(that.description, this.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}