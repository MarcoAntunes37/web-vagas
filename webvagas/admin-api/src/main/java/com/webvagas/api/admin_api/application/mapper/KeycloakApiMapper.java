package com.webvagas.api.admin_api.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.webvagas.api.admin_api.domain.entity.role.Role;
import com.webvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.webvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;

@Mapper(componentModel = "spring")
public interface KeycloakApiMapper {
    @Mapping(target = "id", expression = "java(domain.getDetails().getId())")
    @Mapping(target = "name", expression = "java(domain.getDetails().getName())")
    @Mapping(target = "description", expression = "java(domain.getDetails().getDescription())")
    @Mapping(target = "composite", expression = "java(domain.getAttributes().isComposite())")
    @Mapping(target = "clientRole", expression = "java(domain.getAttributes().isClientRole())")
    @Mapping(target = "containerId", expression = "java(domain.getAttributes().getContainerId())")
    AssignRoleRequest domainToAssignRoleRequest(Role domain);

    @Mapping(target = "id", expression = "java(domain.getDetails().getId())")
    @Mapping(target = "name", expression = "java(domain.getDetails().getName())")
    @Mapping(target = "description", expression = "java(domain.getDetails().getDescription())")
    @Mapping(target = "composite", expression = "java(domain.getAttributes().isComposite())")
    @Mapping(target = "clientRole", expression = "java(domain.getAttributes().isClientRole())")
    @Mapping(target = "containerId", expression = "java(domain.getAttributes().getContainerId())")
    RemoveRoleRequest domainToRemoveRoleRequest(Role domain);
}
