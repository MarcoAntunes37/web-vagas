package com.flashvagas.api.admin_api.application.service.keycloak;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;
import com.flashvagas.api.admin_api.domain.entity.role.enums.PlanRole;
import com.flashvagas.api.admin_api.application.mapper.KeycloakApiMapper;
import com.flashvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.flashvagas.api.admin_api.application.service.keycloak.command.RemovePlanRoleCommand;
import com.flashvagas.api.admin_api.application.service.keycloak.query.ExecuteWithUserQuery;
import com.flashvagas.api.admin_api.domain.entity.role.Role;
import com.flashvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.flashvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth.KeycloakAuthClient;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_role.KeycloakRoleClient;
import com.flashvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user.KeycloakUserClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KeycloakAdminServiceImpl implements KeycloakAdminService {
    private final KeycloakAuthClient kcAuthClient;
    private final KeycloakRoleClient kcRoleClient;
    private final KeycloakUserClient kcUserClient;
    @Autowired
    private KeycloakApiMapper mapper;

    public KeycloakAdminServiceImpl(
            KeycloakAuthClient kcAuthClient,
            KeycloakRoleClient kcRoleClient,
            KeycloakUserClient kcUserClient) {
        this.kcAuthClient = kcAuthClient;
        this.kcRoleClient = kcRoleClient;
        this.kcUserClient = kcUserClient;
    }

    public void assignPlanRole(AssignPlanRoleCommand command) {
        ExecuteWithUserQuery query = new ExecuteWithUserQuery(command.email(), (user, token) -> {
            log.info("user: {}", user);

            log.info("token: {}", token);

            Role role = Optional.ofNullable(PlanRole.fromPlan(command.plan()).toRole())
                    .orElseThrow(() -> new IllegalArgumentException("Plano inválido: " + command.plan()));

            log.info("role: {}", role);

            AssignRoleRequest roleRequest = mapper.domainToAssignRoleRequest(role);

            log.info("roleRequest: {}", roleRequest);
            kcRoleClient.assignRole(user.id(), roleRequest, token);

            log.info("Role '{}' adicionada ao usuário '{}'", role.getDetails().getName(), command.email());
        });

        executeWithUser(query);
    }

    public void removePlanRole(RemovePlanRoleCommand command) {
        ExecuteWithUserQuery query = new ExecuteWithUserQuery(command.email(),
                (user, token) -> {
                    log.info("user: {}", user);

                    log.info("token: {}", token);

                    List<Role> roles = kcRoleClient.getUserRolesMappings(user.id(), token);

                    log.info("roles: {}", roles);

                    for (Role role : roles) {
                        if (PlanRole.isPlanRole(role.getDetails().getName())) {

                            RemoveRoleRequest roleRequest = mapper.domainToRemoveRoleRequest(role);

                            kcRoleClient.removeRole(user.id(), roleRequest, token);

                            log.info("Role {} removida do usuário {}", role.getDetails().getName(), command.email());
                        }
                    }
                });

        executeWithUser(query);
    }

    private void executeWithUser(ExecuteWithUserQuery query) {
        try {
            String token = kcAuthClient.getAccessToken();

            GetUserByEmailResponse user = Optional.ofNullable(kcUserClient.getUserByEmail(query.email(), token))
                    .orElseThrow(
                            () -> new RuntimeException("Usuário com e-mail " + query.email() + " não encontrado."));

            log.info("user: {}", user);
            
            query.action().accept(user, token);

        } catch (Exception e) {
            log.error("Erro ao processar roles para usuário '{}': {}", query.email(), e.getMessage(), e);

            throw new RuntimeException("Erro ao processar roles para o usuário: " + query.email(), e);
        }
    }
}