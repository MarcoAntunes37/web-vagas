package com.webvagas.api.admin_api.application.service.keycloak.query.factory;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.webvagas.api.admin_api.application.service.keycloak.query.ExecuteWithUserQuery;
import com.webvagas.api.admin_api.domain.entity.user.dto.GetUserByEmailResponse;

@Component
public class ExecuteWithUserQueryFactory {
    public ExecuteWithUserQuery execute(String email, BiConsumer<GetUserByEmailResponse, String> action) {
        return new ExecuteWithUserQuery(email, action);
    }
}