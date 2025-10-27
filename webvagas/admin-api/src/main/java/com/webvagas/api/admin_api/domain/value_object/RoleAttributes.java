package com.webvagas.api.admin_api.domain.value_object;

import java.util.Objects;

public class RoleAttributes {
    private Boolean composite;
    private Boolean clientRole;
    private String containerId;

    public RoleAttributes(boolean composite, Boolean clientRole, String containerId) {
        Objects.requireNonNull(containerId, "Container id cannot be null.");

        this.composite = composite;
        this.clientRole = clientRole;
        this.containerId = containerId;
    }

    public Boolean isComposite() {
        return composite;
    }

    public Boolean isClientRole() {
        return clientRole;
    }

    public String getContainerId() {
        return containerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof RoleAttributes))
            return false;

        RoleAttributes that = (RoleAttributes) o;

        return Objects.equals(that.composite, this.composite)
                && Objects.equals(that.clientRole, this.clientRole)
                && Objects.equals(that.containerId, this.containerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(composite, clientRole, containerId);
    }
}