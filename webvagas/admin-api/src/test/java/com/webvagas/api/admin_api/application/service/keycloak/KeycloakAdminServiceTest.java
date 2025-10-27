package com.webvagas.api.admin_api.application.service.keycloak;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.webvagas.api.admin_api.application.mapper.KeycloakApiMapper;
import com.webvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.webvagas.api.admin_api.application.service.keycloak.command.RemovePlanRoleCommand;
import com.webvagas.api.admin_api.application.service.keycloak.query.ExecuteWithUserQuery;
import com.webvagas.api.admin_api.application.service.keycloak.query.factory.ExecuteWithUserQueryExecutor;
import com.webvagas.api.admin_api.application.service.keycloak.query.factory.ExecuteWithUserQueryFactory;
import com.webvagas.api.admin_api.domain.entity.role.Role;
import com.webvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.webvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;
import com.webvagas.api.admin_api.domain.entity.role.enums.PlanRole;
import com.webvagas.api.admin_api.domain.entity.user.User;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;
import com.webvagas.api.admin_api.domain.value_object.UserAttributes;
import com.webvagas.api.admin_api.domain.value_object.UserFullName;
import com.webvagas.api.admin_api.domain.value_object.UserId;
import com.webvagas.api.admin_api.domain.value_object.UserIdentity;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_auth.KeycloakAuthClient;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_role.KeycloakRoleClient;
import com.webvagas.api.admin_api.infrastructure.integration.keycloak.keycloak_user.KeycloakUserClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KeycloakAdminServiceTest {
    private KeycloakAdminService keycloakService;

    @Mock
    private ExecuteWithUserQueryFactory queryFactory;

    @Mock
    private ExecuteWithUserQueryExecutor queryExecutor;

    @Mock
    private KeycloakAuthClient kcAuthClient;

    @Mock
    private KeycloakRoleClient kcRoleClient;

    @Mock
    private KeycloakUserClient kcUserClient;

    @Mock
    private KeycloakApiMapper mapper;

    private String email;

    private String plan;

    private String token;

    private UserId userId;

    private UserFullName fullName;

    private UserIdentity identity;

    private UserAttributes attributes;

    private User user;

    private GetUserByEmailResponse kcUser;

    private Role role;

    @BeforeEach
    public void Setup() {
        MockitoAnnotations.openMocks(this);
        keycloakService = new KeycloakAdminServiceImpl(
                kcAuthClient, kcRoleClient, queryFactory, queryExecutor, mapper);

        email = "email@test.com";

        plan = "plan-start";

        userId = new UserId(UUID.randomUUID());

        fullName = new UserFullName("firstName", "lastName");

        identity = new UserIdentity("username", "email");

        attributes = new UserAttributes(new String[] { "en" }, new String[] { "1234567890" });

        user = new User(userId, fullName, identity, attributes);

        kcUser = new GetUserByEmailResponse(
                userId.getValue().toString(),
                fullName.getFirstName(),
                fullName.getLastName(),
                identity.getEmail(),
                identity.getUsername(),
                attributes);

        token = "mock-token";

        role = PlanRole.fromPlan(plan).toRole();
    }

    @Test
    public void shouldAssignPlanRoleWhenPlanAndUserExists() throws Exception {
        AssignPlanRoleCommand command = new AssignPlanRoleCommand(email, plan);

        AssignRoleRequest assignRoleRequest = mapper.domainToAssignRoleRequest(role);

        when(kcAuthClient.getAccessToken()).thenReturn(token);

        when(mapper.domainToAssignRoleRequest(role)).thenReturn(assignRoleRequest);
        when(queryFactory.execute(anyString(), any()))
                .thenAnswer(invocation -> new ExecuteWithUserQuery(
                        invocation.getArgument(0),
                        invocation.getArgument(1)));

        doAnswer(invocation -> {
            ExecuteWithUserQuery q = invocation.getArgument(0);
            q.action().accept(kcUser, token);
            return null;
        }).when(queryExecutor).execute(any(ExecuteWithUserQuery.class), anyString());

        keycloakService.assignPlanRole(command);

        verify(mapper, atLeastOnce()).domainToAssignRoleRequest(any(Role.class));
        verify(kcRoleClient).assignRole(eq(user.getUserId().getValue().toString()), eq(assignRoleRequest), eq(token));
    }

    @Test
    public void shouldRemovePlanRoleWhenPlanAndUserExists() throws Exception {
        RemovePlanRoleCommand command = new RemovePlanRoleCommand(email);

        RemoveRoleRequest removeRoleRequest = mapper.domainToRemoveRoleRequest(role);

        when(kcRoleClient.getUserRolesMappings(eq(kcUser.id()), anyString()))
                .thenReturn(List.of(role));
        when(mapper.domainToRemoveRoleRequest(role)).thenReturn(removeRoleRequest);
        when(queryFactory.execute(anyString(), any()))
                .thenAnswer(invocation -> new ExecuteWithUserQuery(
                        invocation.getArgument(0),
                        invocation.getArgument(1)));

        when(kcAuthClient.getAccessToken()).thenReturn(token);

        doAnswer(invocation -> {
            ExecuteWithUserQuery q = invocation.getArgument(0);
            q.action().accept(kcUser, token);
            return null;
        }).when(queryExecutor).execute(any(ExecuteWithUserQuery.class), anyString());

        keycloakService.removePlanRole(command);

        verify(mapper, atLeastOnce()).domainToRemoveRoleRequest(any(Role.class));
        verify(kcRoleClient).removeRole(eq(user.getUserId().getValue().toString()), eq(removeRoleRequest), eq(token));
    }
}