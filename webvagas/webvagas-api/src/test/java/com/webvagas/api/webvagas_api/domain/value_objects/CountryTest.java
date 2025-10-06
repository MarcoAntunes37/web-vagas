package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.Country;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {
    @Test
    void shouldNotCreateCountryFromNullValue() {
        assertThatThrownBy(() -> new Country(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Country cannot be null.");
    }

    @Test
    void shouldNotCreateCountryFromEmptyValue() {
        assertThatThrownBy(() -> new Country(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country cannot be empty");
    }

    @Test
    void shouldNotCreateCountryWithMoreThan2Characters() {
        assertThatThrownBy(() -> new Country("abcde"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country must have 2 characters");
    }

    @Test
    void shouldNotCreateCountryWithInvalidCountryCode() {
        assertThatThrownBy(() -> new Country("XX"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Country must be a valid country code");
    }

    @Test
    void shouldCreateCountry() {
        Country country = new Country("BR");

        assertThat(country.getValue()).isEqualTo("BR");
    }

    @Test
    void shouldBeEqualWhenAttributesAreTheSame() {
        Country country1 = new Country("BR");
        Country country2 = new Country("BR");

        assertThat(country1).isEqualTo(country2);
        assertThat(country1.hashCode()).isEqualTo(country2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenAttributesAreDifferent() {
        Country country1 = new Country("BR");
        Country country2 = new Country("US");

        assertThat(country1).isNotEqualTo(country2);
        assertThat(country1.hashCode()).isNotEqualTo(country2.hashCode());
    }
}