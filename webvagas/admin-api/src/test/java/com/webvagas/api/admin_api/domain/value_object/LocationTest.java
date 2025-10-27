package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest {
    private String region;
    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;

    @BeforeEach
    void setUp() {
        region = "region";
        city = "city";
        state = "state";
        country = "country";
        latitude = 1.0;
        longitude = 1.0;
    }

    @Test
    void shouldFailToCreateWhenWhenRegionIsNull() {
        region = null;

        assertThatThrownBy(() -> new Location(region, city, state, country, latitude, longitude))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Region cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenCityIsNull() {
        city = null;

        assertThatThrownBy(() -> new Location(region, city, state, country, latitude, longitude))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("City cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenStateIsNull() {
        state = null;

        assertThatThrownBy(() -> new Location(region, city, state, country, latitude, longitude))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("State cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenCountryIsNull() {
        country = null;

        assertThatThrownBy(() -> new Location(region, city, state, country, latitude, longitude))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Country cannot be null.");
    }

    @Test
    void shouldCreateLocation() {
        Location location = new Location(region, city, state, country, latitude, longitude);

        assertThat(location).isNotNull();
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        Location location = new Location(region, city, state, country, latitude, longitude);

        region = "region2";
        city = "city2";
        state = "state2";
        country = "country2";
        latitude = 2.0;
        longitude = 2.0;

        Location location2 = new Location(region, city, state, country, latitude, longitude);

        assertThat(location.equals(location2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        Location location = new Location(region, city, state, country, latitude, longitude);

        Location location2 = new Location(region, city, state, country, latitude, longitude);

        assertThat(location.equals(location2)).isTrue();
    }
}