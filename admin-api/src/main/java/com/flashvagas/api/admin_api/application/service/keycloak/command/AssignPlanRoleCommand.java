package com.flashvagas.api.admin_api.application.service.keycloak.command;

public record AssignPlanRoleCommand(
        String email,
        String plan) {
}