package com.webvagas.api.admin_api.domain.entity.role.enums;

import com.webvagas.api.admin_api.domain.entity.role.Role;
import com.webvagas.api.admin_api.domain.value_object.RoleAttributes;
import com.webvagas.api.admin_api.domain.value_object.RoleDetails;

public enum PlanRole {
    PLAN_START("plan-start", "d7e99405-35b5-46da-9155-2fa097728f84", "role_plan_start"),
    PLAN_TURBO("plan-turbo", "1055f149-2dbf-4898-bee8-649bf31dc6f5", "role_plan_turbo");

    private final String name;
    private final String id;
    private final String description;

    PlanRole(String name, String id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public static PlanRole fromPlan(String plan) {
        for (PlanRole pr : values()) {
            if (pr.name.equals(plan)) {
                return pr;
            }
        }
        throw new IllegalArgumentException("Plano inválido: " + plan);
    }

    public static boolean isPlanRole(String roleName) {
        for (PlanRole pr : values()) {
            if (pr.name.equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public Role toRole() {
        RoleDetails details = new RoleDetails(
                this.id,
                this.name,
                this.description);

        RoleAttributes attributes = new RoleAttributes(
                false,
                false,
                "181ebbfd-bcfb-4afe-9a27-e5791d743d1f");

        Role role = new Role(details, attributes);

        return role;
    }
}
