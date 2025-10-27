package com.webvagas.api.admin_api.application.service.keycloak;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webvagas.api.admin_api.domain.entity.role.enums.PlanRole;
import com.webvagas.api.admin_api.application.mapper.KeycloakApiMapper;
import com.webvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.webvagas.api.admin_api.application.service.keycloak.command.RemovePlanRoleCommand;
import com.webvagas.api.admin_api.application.service.keycloak.query.ExecuteWithUserQuery;
import com.webvagas.api.admin_api.application.service.keycloak.query.factory.ExecuteWithUserQueryExecutor;
import com.webvagas.api.admin_api.application.service.keycloak.query.factory.ExecuteWithUserQueryFactory;
import com.webvagas.api.admin_api.domain.entity.role.Role;
import com.webvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.webvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth.KeycloakAuthClient;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_role.KeycloakRoleClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KeycloakAdminServiceImpl implements KeycloakAdminService {
    private final KeycloakAuthClient kcAuthClient;
    private final KeycloakRoleClient kcRoleClient;
    private final ExecuteWithUserQueryFactory executeWithUserQueryFactory;
    private final ExecuteWithUserQueryExecutor executeWithUserQueryExecutor;
    @Autowired
    private KeycloakApiMapper mapper;

    public KeycloakAdminServiceImpl(
            KeycloakAuthClient kcAuthClient,
            KeycloakRoleClient kcRoleClient,
            ExecuteWithUserQueryFactory executeWithUserQueryFactory,
            ExecuteWithUserQueryExecutor executeWithUserQueryExecutor,
            KeycloakApiMapper mapper) {
        this.kcAuthClient = kcAuthClient;
        this.kcRoleClient = kcRoleClient;
        this.executeWithUserQueryFactory = executeWithUserQueryFactory;
        this.executeWithUserQueryExecutor = executeWithUserQueryExecutor;
        this.mapper = mapper;
    }

    public void assignPlanRole(AssignPlanRoleCommand command) {
        String token;

        try {
            token = kcAuthClient.getAccessToken();
        } catch (Exception e) {
            log.info("Erro ao obter token.");
            return;
        }

        ExecuteWithUserQuery query = executeWithUserQueryFactory.execute(command.email(), (user, t) -> {
            Role role = Optional.ofNullable(PlanRole.fromPlan(command.plan()).toRole())
                    .orElseThrow(() -> new IllegalArgumentException("Plano inválido: " + command.plan()));

            AssignRoleRequest roleRequest = mapper.domainToAssignRoleRequest(role);

            kcRoleClient.assignRole(user.id(), roleRequest, t);

            log.info("Role '{}' adicionada ao usuário '{}'", role.getDetails().getName(), command.email());
        });

        executeWithUserQueryExecutor.execute(query, token);
    }

    public void removePlanRole(RemovePlanRoleCommand command) {
        String token;

        try {
            token = kcAuthClient.getAccessToken();
        } catch (Exception e) {
            log.info("Erro ao obter token.");
            return;
        }

        ExecuteWithUserQuery query = executeWithUserQueryFactory.execute(command.email(), (user, t) -> {
            log.info("user: {}", user);
            log.info("token: {}", t);

            List<Role> roles = kcRoleClient.getUserRolesMappings(user.id(), t);
            log.info("roles: {}", roles);

            for (Role role : roles) {
                if (PlanRole.isPlanRole(role.getDetails().getName())) {
                    RemoveRoleRequest roleRequest = mapper.domainToRemoveRoleRequest(role);
                    kcRoleClient.removeRole(user.id(), roleRequest, t);

                    log.info("Role {} removida do usuário {}", role.getDetails().getName(), command.email());
                }
            }
        });

        executeWithUserQueryExecutor.execute(query, token);
    }
}