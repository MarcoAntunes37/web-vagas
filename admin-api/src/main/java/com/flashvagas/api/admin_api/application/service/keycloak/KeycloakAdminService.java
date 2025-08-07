package com.flashvagas.api.admin_api.application.service.keycloak;

import com.flashvagas.api.admin_api.application.service.keycloak.command.AssignPlanRoleCommand;
import com.flashvagas.api.admin_api.application.service.keycloak.command.RemovePlanRoleCommand;

public interface KeycloakAdminService {
    void assignPlanRole(AssignPlanRoleCommand command);

    void removePlanRole(RemovePlanRoleCommand command);
}
