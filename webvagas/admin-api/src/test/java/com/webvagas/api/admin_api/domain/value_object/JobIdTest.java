package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class JobIdTest {
    private String value;

    @BeforeEach
    void setUp() {
        value = "jobId";
    }

    @Test
    void shouldFailToCreateWhenWhenJobIdIsNull() {
        value = null;

        assertThatThrownBy(() -> new JobId(value))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("JobId cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenJobIdIsEmpty() {
        value = "";

        assertThatThrownBy(() -> new JobId(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("JobId cannot be empty.");
    }

    @Test
    void shouldCreateJobId() {
        JobId jobId = new JobId(value);

        assertThat(jobId).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        JobId jobId = new JobId(value);

        value = "jobId2";

        JobId jobId2 = new JobId(value);

        assertThat(jobId.equals(jobId2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        JobId jobId = new JobId(value);

        JobId jobId2 = new JobId(value);

        assertThat(jobId.equals(jobId2)).isTrue();
    }
}