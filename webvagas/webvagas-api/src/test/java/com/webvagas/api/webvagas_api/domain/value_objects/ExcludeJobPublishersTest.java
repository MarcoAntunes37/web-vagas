package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExcludeJobPublishersTest {

    @Test
    void shouldNotCreateExcludeJobPublishersWithNullValue() {
        assertThatThrownBy(() -> new ExcludeJobPublishers(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldCreateExcludeJobPublishers() {
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");

        ExcludeJobPublishers excludeJobPublishers1 = new ExcludeJobPublishers(excludeJobPublishers);

        assertThat(excludeJobPublishers1.getValue()).isEqualTo(excludeJobPublishers);
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");

        ExcludeJobPublishers excludeJobPublishers1 = new ExcludeJobPublishers(excludeJobPublishers);
        ExcludeJobPublishers excludeJobPublishers2 = new ExcludeJobPublishers(excludeJobPublishers);

        assertThat(excludeJobPublishers1.equals(excludeJobPublishers2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");

        ExcludeJobPublishers excludeJobPublishers1 = new ExcludeJobPublishers(excludeJobPublishers);
        ExcludeJobPublishers excludeJobPublishers2 = new ExcludeJobPublishers(Set.of("publisher1", "publisher2"));

        assertThat(excludeJobPublishers1.equals(excludeJobPublishers2)).isFalse();
    }

    @Test
    void shouldReturnJoinedStringSeparatedByComma() {
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");

        ExcludeJobPublishers excludeJobPublishers1 = new ExcludeJobPublishers(excludeJobPublishers);

        assertThat(excludeJobPublishers1.toString()).isInstanceOf(String.class);
        assertThat(excludeJobPublishers1.toString()).isEqualTo("publisher1,publisher2,publisher3");
    }
}
