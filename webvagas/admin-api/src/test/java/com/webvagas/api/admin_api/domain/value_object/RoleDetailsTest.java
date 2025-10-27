package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleDetailsTest {
    private String id;
    private String name;
    private String description;

    @BeforeEach
    void setUp() {
        id = "1";
        name = "test";
        description = "test";
    }

    @Test
    void shouldFailToCreateWhenWhenIdIsNull() {
        id = null;
        assertThatThrownBy(() -> new RoleDetails(id, name, description))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Id cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenNameIsNull() {
        name = null;
        assertThatThrownBy(() -> new RoleDetails(id, name, description))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name cannot be null.");
    }

    @Test
    void shouldCreateRoleDetails() {
        RoleDetails salaryRange = new RoleDetails(id, name, description);

        assertThat(salaryRange).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        RoleDetails salaryRange = new RoleDetails(id, name, description);

        id = "2";
        name = "test2";
        description = "test2";

        RoleDetails salaryRange2 = new RoleDetails(id, name, description);

        assertThat(salaryRange.equals(salaryRange2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        RoleDetails salaryRange = new RoleDetails(id, name, description);

        RoleDetails salaryRange2 = new RoleDetails(id, name, description);

        assertThat(salaryRange.equals(salaryRange2)).isTrue();
    }
}