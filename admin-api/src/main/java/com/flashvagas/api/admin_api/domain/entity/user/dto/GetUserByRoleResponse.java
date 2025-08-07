package com.flashvagas.api.admin_api.domain.entity.user.dto;

public record GetUserByRoleResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String username,
        Attributes attributes) {
}