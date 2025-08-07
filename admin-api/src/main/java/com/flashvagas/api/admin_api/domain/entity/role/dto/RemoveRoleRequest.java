package com.flashvagas.api.admin_api.domain.entity.role.dto;

public record RemoveRoleRequest(
        String id,
        String name,
        String description,
        boolean composite,
        boolean clientRole,
        String containerId) {

}
