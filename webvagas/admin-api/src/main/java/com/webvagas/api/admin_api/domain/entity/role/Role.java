package com.webvagas.api.admin_api.domain.entity.role;

import com.webvagas.api.admin_api.domain.value_object.RoleAttributes;
import com.webvagas.api.admin_api.domain.value_object.RoleDetails;

public class Role {
    private RoleDetails details;
    private RoleAttributes attributes;

    public Role(RoleDetails details, RoleAttributes attributes) {
        this.details = details;
        this.attributes = attributes;
    }

    public RoleDetails getDetails() {
        return details;
    }

    public RoleAttributes getAttributes() {
        return attributes;
    }

    public String getRoleDetailsId() {
        return details.getId();
    }

    public String getRoleDetailsName() {
        return details.getName();
    }

    public String getRoleDetailsDescription() {
        return details.getDescription();
    }

    public Boolean getRoleAttributesComposite() {
        return attributes.isComposite();
    }

    public Boolean getRoleAttributesClientRole() {
        return attributes.isClientRole();
    }

    public String getRoleAttributesContainerId() {
        return attributes.getContainerId();
    }
}