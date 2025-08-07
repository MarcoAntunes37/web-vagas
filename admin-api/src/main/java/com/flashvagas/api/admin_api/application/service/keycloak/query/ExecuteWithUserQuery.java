package com.flashvagas.api.admin_api.application.service.keycloak.query;

import java.util.function.BiConsumer;

import com.flashvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;

public record ExecuteWithUserQuery(
        String email,
        BiConsumer<GetUserByEmailResponse, String> action) {
}
