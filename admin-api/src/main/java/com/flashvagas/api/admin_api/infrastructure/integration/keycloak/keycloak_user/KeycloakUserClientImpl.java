package com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;
import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByRoleResponse;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.utils.KeycloakClientUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakUserClientImpl implements KeycloakUserClient {
    private final RestTemplate restTemplate;

    @SuppressWarnings("null")
    public GetUserByEmailResponse getUserByEmail(String email, String token) {
        String url = KeycloakClientUtils.buildUrlGetByEmail(email);

        log.info("url: {}", url);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<GetUserByEmailResponse[]> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                GetUserByEmailResponse[].class);

        GetUserByEmailResponse[] responseArray = response.getBody();

        if (responseArray[0] == null) {
            throw new RuntimeException("Usuário com e-mail " + email + " não encontrado.");
        }

        return Optional.ofNullable(responseArray)
                .filter(u -> u.length > 0)
                .map(u -> u[0])
                .orElseThrow(() -> new RuntimeException("Usuário com e-mail " + email + " não encontrado."));
    }

    public List<GetUserByRoleResponse> getUsersByRole(String roleName, String token) {
        String url = KeycloakClientUtils.buildUrlGetByRole(roleName);

        HttpHeaders headers = KeycloakClientUtils.createAuthHeaders(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GetUserByRoleResponse[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    GetUserByRoleResponse[].class);

            List<GetUserByRoleResponse> users = Arrays.asList(response.getBody());

            return users;
        } catch (Exception e) {
            log.error("Erro ao buscar usuários da role {}", roleName, e);
            return Collections.emptyList();
        }
    }
}