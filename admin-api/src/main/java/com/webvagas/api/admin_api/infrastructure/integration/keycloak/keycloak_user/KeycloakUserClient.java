package com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user;

import java.util.List;

import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;

public interface KeycloakUserClient {
    GetUserByEmailResponse getUserByEmail(String email, String token);

    List<GetUserByRoleResponse> getUsersByRole(String roleName, String token);
}
