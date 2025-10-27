package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class PublisherTest {
    private String name;

    @BeforeEach
    void setUp() {
        name = "name";
    }

    @Test
    void shouldFailToCreateWhenWhenNameIsNull() {
        name = null;

        assertThatThrownBy(() -> new Publisher(name))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name cannot be null.");
    }

    @Test
    void shouldCreatePublisher() {
        Publisher publisher = new Publisher(name);

        assertThat(publisher).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Publisher publisher = new Publisher(name);

        name = "name2";

        Publisher publisher2 = new Publisher(name);

        assertThat(publisher.equals(publisher2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Publisher publisher = new Publisher(name);

        Publisher publisher2 = new Publisher(name);

        assertThat(publisher.equals(publisher2)).isTrue();
    }
}