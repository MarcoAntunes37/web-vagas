package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.JobId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class JobIdTest {
    @Test
    void shouldNotCreateJobIdWithNullValue() {
        assertThatThrownBy(() -> new JobId(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("JobId cannot be null.");
    }

    @Test
    void shouldNotCreateJobIdWithEmptyValue() {
        assertThatThrownBy(() -> new JobId(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("JobId cannot be empty.");
    }

    @Test
    void shouldCreateJobId() {
        JobId jobId = new JobId("1234567890");

        assertThat(jobId.getValue()).isEqualTo("1234567890");
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        JobId jobId1 = new JobId("1234567890");
        JobId jobId2 = new JobId("1234567890");

        assertThat(jobId1.equals(jobId2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        JobId jobId1 = new JobId("1234567890");
        JobId jobId2 = new JobId("1234567891");

        assertThat(jobId1.equals(jobId2)).isFalse();
    }
}