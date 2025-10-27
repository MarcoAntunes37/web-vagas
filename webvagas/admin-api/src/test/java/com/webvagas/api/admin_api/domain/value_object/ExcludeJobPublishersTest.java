package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ExcludeJobPublishersTest {
    private Set<String> values;

    @BeforeEach
    void setUp() {
        values = Set.of("publisher1", "publisher2", "publisher3");
    }

    @Test
    void shouldFailToCreateWhenWhenExcludeJobPublishersIsNull() {
        values = null;

        assertThatThrownBy(() -> new ExcludeJobPublishers(values))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("ExcludeJobPublishers cannot be null.");
    }

    @Test
    void shouldCreateEmptySetWhenWhenExcludeJobPublishersIsEmpty() {
        values = Set.of();

        assertThat(values).isEmpty();
    }

    @Test
    void shouldCreateExcludeJobPublishers() {
        ExcludeJobPublishers excludeJobPublishers = new ExcludeJobPublishers(values);

        assertThat(excludeJobPublishers).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        ExcludeJobPublishers excludeJobPublishers = new ExcludeJobPublishers(values);

        values = Set.of("publisher4", "publisher5");

        ExcludeJobPublishers excludeJobPublishers2 = new ExcludeJobPublishers(values);

        assertThat(excludeJobPublishers.equals(excludeJobPublishers2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        ExcludeJobPublishers excludeJobPublishers = new ExcludeJobPublishers(values);

        ExcludeJobPublishers excludeJobPublishers2 = new ExcludeJobPublishers(values);

        assertThat(excludeJobPublishers.equals(excludeJobPublishers2)).isTrue();
    }
}