package com.webvagas.api.admin_api.domain.entity.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webvagas.api.admin_api.domain.value_object.RoleAttributes;
import com.webvagas.api.admin_api.domain.value_object.RoleDetails;

public class RoleTest {
    private RoleDetails details;
    private RoleAttributes attributes;

    @BeforeEach
    void Setup() {
        details = new RoleDetails("1", "test", "test");
        attributes = new RoleAttributes(true, true, "1");
    }

    @Test
    void shouldCreateRole() {
        Role role = new Role(details, attributes);

        assertThat(role.getDetails()).isEqualTo(details);
    }
}