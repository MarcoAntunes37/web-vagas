package com.webvagas.api.admin_api.application.service.keycloak;

import com.webvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.webvagas.api.admin_api.application.service.keycloak.command.RemovePlanRoleCommand;

public interface KeycloakAdminService {
    void assignPlanRole(AssignPlanRoleCommand command);

    void removePlanRole(RemovePlanRoleCommand command);
}
