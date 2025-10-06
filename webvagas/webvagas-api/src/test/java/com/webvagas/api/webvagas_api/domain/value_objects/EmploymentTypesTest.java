package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EmploymentTypesTest {

    @Test
    void shouldCreateEmploymentTypesFromEmptySetWithEmptyValue() {
        Set<EmploymentType> types = Set.of();
        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.values()).isEmpty();
    }

    @Test
    void shouldCreateEmploymentTypes() {
        Set<EmploymentType> types = Set.of(EmploymentType.FULLTIME);
        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.contains(EmploymentType.FULLTIME)).isTrue();
    }

    @Test
    void shouldReturnTrueWhenContainsEmploymentType() {
        Set<EmploymentType> types = Set.of(EmploymentType.CONTRACTOR, EmploymentType.INTERN, EmploymentType.FULLTIME,
                EmploymentType.PARTTIME);
        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.contains(EmploymentType.CONTRACTOR)).isTrue();
        assertThat(employmentTypes.contains(EmploymentType.INTERN)).isTrue();
        assertThat(employmentTypes.contains(EmploymentType.FULLTIME)).isTrue();
        assertThat(employmentTypes.contains(EmploymentType.PARTTIME)).isTrue();
    }

    @Test
    void shouldReturnfalseWhenNotContainsEmploymentType() {
        Set<EmploymentType> types = Set.of();
        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.contains(EmploymentType.CONTRACTOR)).isFalse();
        assertThat(employmentTypes.contains(EmploymentType.INTERN)).isFalse();
        assertThat(employmentTypes.contains(EmploymentType.FULLTIME)).isFalse();
        assertThat(employmentTypes.contains(EmploymentType.PARTTIME)).isFalse();
    }

    @Test
    void shouldReturnJoinedStringSeparatedByComma() {
        Set<EmploymentType> types = Set.of(EmploymentType.CONTRACTOR, EmploymentType.INTERN, EmploymentType.FULLTIME,
                EmploymentType.PARTTIME);

        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.toString()).isInstanceOf(String.class);
        assertThat(employmentTypes.toString()).isEqualTo("CONTRACTOR,INTERN,FULLTIME,PARTTIME");
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Set<EmploymentType> types = Set.of(EmploymentType.CONTRACTOR, EmploymentType.INTERN, EmploymentType.FULLTIME,
                EmploymentType.PARTTIME);
        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.equals(employmentTypes)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Set<EmploymentType> types = Set.of(EmploymentType.CONTRACTOR, EmploymentType.INTERN, EmploymentType.FULLTIME,
                EmploymentType.PARTTIME);
        EmploymentTypes employmentTypes = new EmploymentTypes(types);

        assertThat(employmentTypes.equals(new EmploymentTypes(Set.of()))).isFalse();
    }
}