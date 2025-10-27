package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleAttributesTest {
    private Boolean composite;
    private Boolean clientRole;
    private String containerId;

    @BeforeEach
    void setUp() {
        composite = true;
        clientRole = true;
        containerId = "1";
    }

    @Test
    void shouldFailToCreateWhenWhenContainerIdIsNull() {
        containerId = null;

        assertThatThrownBy(() -> new RoleAttributes(composite, clientRole, containerId))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Container id cannot be null.");
    }

    @Test
    void shouldCreateRoleAttributes() {
        RoleAttributes roleAttributes = new RoleAttributes(composite, clientRole, containerId);

        assertThat(roleAttributes).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        RoleAttributes roleAttributes = new RoleAttributes(composite, clientRole, containerId);

        composite = false;
        clientRole = false;
        containerId = "2";

        RoleAttributes roleAttributes2 = new RoleAttributes(composite, clientRole, containerId);

        assertThat(roleAttributes.equals(roleAttributes2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        RoleAttributes roleAttributes = new RoleAttributes(composite, clientRole, containerId);

        RoleAttributes roleAttributes2 = new RoleAttributes(composite, clientRole, containerId);

        assertThat(roleAttributes.equals(roleAttributes2)).isTrue();
    }
}