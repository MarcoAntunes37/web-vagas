package com.flashvagas.api.admin_api.domain.entity.role.enums;

import com.flashvagas.api.admin_api.domain.entity.role.Role;
import com.flashvagas.api.admin_api.domain.value_object.RoleAttributes;
import com.flashvagas.api.admin_api.domain.value_object.RoleDetails;

public enum PlanRole {
    PLAN_ESSENTIALS("plan-essentials", "d4827e39-7402-4966-8706-bff0007c0fbf"),
    PLAN_TURBO("plan-turbo", "1055f149-2dbf-4898-bee8-649bf31dc6f5");

    private final String name;
    private final String id;

    PlanRole(String name, String id) {
        this.name = name;
        this.id = id;
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
                this.id, this.name,
                "${" + this.name + "}");

        RoleAttributes attributes = new RoleAttributes(
                false,
                false,
                "181ebbfd-bcfb-4afe-9a27-e5791d743d1f");

        Role role = new Role(details, attributes);

        return role;
    }
}
