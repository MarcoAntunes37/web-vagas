package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {
    private String value;

    @BeforeEach
    void setUp() {
        value = "BR";
    }

    @Test
    void shouldFailToCreateWhenWhenCountryIsNull() {
        value = null;

        assertThatThrownBy(() -> new Country(value))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Country cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenCountryHaveMoreOrLessThanTwoCharacters() {
        value = "BRA";

        assertThatThrownBy(() -> new Country(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country must have 2 characters.");
        value = "B";

        assertThatThrownBy(() -> new Country(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country must have 2 characters.");
    }

    @Test
    void shouldCreateCountry() {
        Country country = new Country(value);

        assertThat(country).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Country country = new Country(value);

        value = "US";

        Country country2 = new Country(value);

        assertThat(country.equals(country2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Country country = new Country(value);

        Country country2 = new Country(value);

        assertThat(country.equals(country2)).isTrue();
    }
}