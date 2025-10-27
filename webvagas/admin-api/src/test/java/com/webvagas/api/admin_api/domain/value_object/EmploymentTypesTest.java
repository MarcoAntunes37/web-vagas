package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webvagas.api.admin_api.domain.entity.user_preferences.enums.EmploymentType;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EmploymentTypesTest {
    private Set<EmploymentType> values;

    @BeforeEach
    void setUp() {
        values = Set.of(EmploymentType.CONTRACTOR, EmploymentType.FULLTIME);
    }

    @Test
    void shouldFailToCreateWhenWhenEmploymentTypesIsNull() {
        values = null;

        assertThatThrownBy(() -> new EmploymentTypes(values))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Employment types cannot be null.");
    }

    @Test
    void shouldCreateEmploymentTypesEmpty() {
        values = Set.of();

        assertThat(values.isEmpty()).isTrue();
    }

    @Test
    void shouldCreateEmploymentTypes() {
        EmploymentTypes employmentTypes = new EmploymentTypes(values);

        assertThat(employmentTypes).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        EmploymentTypes employmentTypes = new EmploymentTypes(values);

        values = Set.of(EmploymentType.CONTRACTOR);

        EmploymentTypes employmentTypes2 = new EmploymentTypes(values);

        assertThat(employmentTypes.equals(employmentTypes2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        EmploymentTypes employmentTypes = new EmploymentTypes(values);

        EmploymentTypes employmentTypes2 = new EmploymentTypes(values);

        assertThat(employmentTypes.equals(employmentTypes2)).isTrue();
    }
}