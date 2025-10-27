package com.webvagas.api.admin_api.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.webvagas.api.admin_api.domain.entity.role.Role;
import com.webvagas.api.admin_api.domain.entity.role.dto.AssignRoleRequest;
import com.webvagas.api.admin_api.domain.entity.role.dto.RemoveRoleRequest;
import com.webvagas.api.admin_api.domain.value_object.RoleAttributes;
import com.webvagas.api.admin_api.domain.value_object.RoleDetails;

public class KeycloakApiMapperTest {
    private KeycloakApiMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(KeycloakApiMapper.class);
    }

    @Test
    void shouldMapDomainToAssignRoleRequestCorrectly() {

        RoleDetails roleDetails = new RoleDetails("id", "name", "description");

        RoleAttributes roleAttributes = new RoleAttributes(true, true, "containerId");

        Role role = new Role(roleDetails, roleAttributes);

        AssignRoleRequest assignRoleRequest = mapper.domainToAssignRoleRequest(role);

        assertThat(assignRoleRequest).isNotNull();
        assertThat(assignRoleRequest.id()).isEqualTo(roleDetails.getId());
        assertThat(assignRoleRequest.name()).isEqualTo(roleDetails.getName());
        assertThat(assignRoleRequest.description()).isEqualTo(roleDetails.getDescription());
        assertThat(assignRoleRequest.composite()).isEqualTo(roleAttributes.isComposite());
        assertThat(assignRoleRequest.clientRole()).isEqualTo(roleAttributes.isClientRole());
        assertThat(assignRoleRequest.containerId()).isEqualTo(roleAttributes.getContainerId());
    }

    @Test
    void shouldMapDomainToRemoveRoleRequestCorrectly() {
        RoleDetails roleDetails = new RoleDetails("id", "name", "description");

        RoleAttributes roleAttributes = new RoleAttributes(true, true, "containerId");

        Role role = new Role(roleDetails, roleAttributes);

        RemoveRoleRequest removeRoleRequest = mapper.domainToRemoveRoleRequest(role);
        
        assertThat(removeRoleRequest).isNotNull();
        assertThat(removeRoleRequest.id()).isEqualTo(roleDetails.getId());
        assertThat(removeRoleRequest.name()).isEqualTo(roleDetails.getName());
        assertThat(removeRoleRequest.description()).isEqualTo(roleDetails.getDescription());
        assertThat(removeRoleRequest.composite()).isEqualTo(roleAttributes.isComposite());
        assertThat(removeRoleRequest.clientRole()).isEqualTo(roleAttributes.isClientRole());
        assertThat(removeRoleRequest.containerId()).isEqualTo(roleAttributes.getContainerId());
    }
}