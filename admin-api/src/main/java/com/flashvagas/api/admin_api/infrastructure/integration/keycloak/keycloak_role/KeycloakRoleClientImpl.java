package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.role.Role;
import com.flashvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.flashvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;
import com.flashvagas.api.admin_api.domain.entity.role.dto.RoleMappingsResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.utils.KeycloakClientUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeycloakRoleClientImpl implements KeycloakRoleClient {
    private final RestTemplate restTemplate;
    @Value("${keycloak.host}")
    private String host;
    @Value("${keycloak.realm}")
    private String realm;

    public void assignRole(String userId, AssignRoleRequest role, String token) {
        String url = KeycloakClientUtils.buildUrlUpdateRole(userId);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        HttpEntity<AssignRoleRequest[]> request = new HttpEntity<>(new AssignRoleRequest[]{role}, headers);

        restTemplate.postForEntity(url, request, String.class);
    }

    public void removeRole(String userId, RemoveRoleRequest role, String token) {
        String url = KeycloakClientUtils.buildUrlUpdateRole(userId);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        HttpEntity<RemoveRoleRequest[]> request = new HttpEntity<>(new RemoveRoleRequest[]{role}, headers);

        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }

    public List<Role> getUserRolesMappings(String userId, String token) {
        String url = KeycloakClientUtils.buidlUrlGetUserRoles(userId);

        HttpEntity<Void> entity = new HttpEntity<>(KeycloakClientUtils.createAuthHeaders(token));

        ResponseEntity<RoleMappingsResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                RoleMappingsResponse.class);

        RoleMappingsResponse roleMappingsResponse = response.getBody();

        return Optional.ofNullable(roleMappingsResponse)
                .map(RoleMappingsResponse::realmMappings)
                .orElse(new ArrayList<>());
    }
}
