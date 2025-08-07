package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_role;

import java.util.List;

import com.flashvagas.api.admin_api.domain.entity.role.Role;
import com.flashvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.flashvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;

public interface KeycloakRoleClient {
    void assignRole(String userId, AssignRoleRequest role, String token);

    void removeRole(String userId, RemoveRoleRequest role, String token);

    List<Role> getUserRolesMappings(String userId, String token);
}