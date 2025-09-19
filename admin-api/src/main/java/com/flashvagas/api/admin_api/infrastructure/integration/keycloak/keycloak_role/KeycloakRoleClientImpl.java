package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashvagas.api.admin_api.domain.entity.role.Role;
import com.flashvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.flashvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;
import com.flashvagas.api.admin_api.domain.entity.role.dto.RoleMappingsResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.utils.KeycloakClientUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakRoleClientImpl implements KeycloakRoleClient {
    private final RestTemplate restTemplate;

    public void assignRole(String userId, AssignRoleRequest role, String token) {
        String url = KeycloakClientUtils.buildUrlUpdateRole(userId);

        log.info("url: {}", url);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("headers: {}", headers);

        log.info("rolebeforeHttpEntity: {}", role);

        List<AssignRoleRequest> roleList = new ArrayList<>();
        roleList.add(role);

        HttpEntity<List<AssignRoleRequest>> request = new HttpEntity<>(roleList, headers);

        log.info("request: {}", request);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            log.info(objectMapper.writeValueAsString(roleList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        restTemplate.postForEntity(url, request, Void.class);
    }

    public void removeRole(String userId, RemoveRoleRequest role, String token) {
        String url = KeycloakClientUtils.buildUrlUpdateRole(userId);

        log.info("url: {}", url);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        headers.set("Content-Type", "application/json");

        log.info("headers: {}", headers);

        HttpEntity<RemoveRoleRequest[]> request = new HttpEntity<>(new RemoveRoleRequest[] { role }, headers);

        log.info("request: {}", request);

        restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
    }

    public List<Role> getUserRolesMappings(String userId, String token) {
        String url = KeycloakClientUtils.buidlUrlGetUserRoles(userId);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        headers.set("Content-Type", "application/json");

        log.info("response: {}", headers);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        log.info("response: {}", entity);

        ResponseEntity<RoleMappingsResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                RoleMappingsResponse.class);

        log.info("response: {}", response);

        RoleMappingsResponse roleMappingsResponse = response.getBody();

        return Optional.ofNullable(roleMappingsResponse)
                .map(RoleMappingsResponse::realmMappings)
                .orElse(new ArrayList<>());
    }
}
