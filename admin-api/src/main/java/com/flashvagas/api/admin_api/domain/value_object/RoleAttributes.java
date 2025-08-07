package com.flashvagas.api.admin_api.domain.value_object;

public class RoleAttributes {
    private boolean composite;
    private boolean clientRole;
    private String containerId;

    public RoleAttributes(boolean composite, boolean clientRole, String containerId) {
        this.composite = composite;
        this.clientRole = clientRole;
        this.containerId = containerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof RoleAttributes))
            return false;

        RoleAttributes roleAttributes = (RoleAttributes) o;

        return composite == roleAttributes.composite &&
                clientRole == roleAttributes.clientRole &&
                containerId.equals(roleAttributes.containerId);
    }

    @Override
    public int hashCode() {
        int result = composite ? 1 : 0;

        result = 31 * result + (clientRole ? 1 : 0);

        result = 31 * result + containerId.hashCode();

        return result;
    }

    @Override
    public String toString() {
        return "RoleAttributes{" +
                "composite=" + composite +
                ", clientRole=" + clientRole +
                ", containerId='" + containerId + '\'' +
                '}';
    }

    public boolean isComposite() {
        return composite;
    }

    public boolean isClientRole() {
        return clientRole;
    }

    public String getContainerId() {
        return containerId;
    }
}
