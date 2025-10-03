package com.webvagas.api.admin_api.domain.value_object;

public class RoleDetails {
    private String id;
    private String name;
    private String description;

    public RoleDetails(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof RoleDetails))
            return false;

        RoleDetails roleDetails = (RoleDetails) o;

        return id.equals(roleDetails.id) &&
                name.equals(roleDetails.name) &&
                description.equals(roleDetails.description);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RoleDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
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
}
