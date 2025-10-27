package com.webvagas.api.admin_api.domain.value_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchFiltersTest {
    private Country country;
    private Boolean remoteWork;
    private ExcludeJobPublishers excludeJobPublishers;

    @BeforeEach
    void setUp() {
        country = new Country("BR");
        remoteWork = true;
        excludeJobPublishers = new ExcludeJobPublishers(Set.of("publisher1", "publisher2", "publisher3"));
    }

    @Test
    void shouldFailToCreateWhenWhenRemoteWorkIsNull() {
        remoteWork = null;

        assertThatThrownBy(() -> new SearchFilters(remoteWork, country, excludeJobPublishers))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Remote work cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenCountryIsNull() {
        Country country = null;

        assertThatThrownBy(() -> new SearchFilters(remoteWork, country, excludeJobPublishers))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Country cannot be null.");
    }

    @Test
    void shouldFailToCreateWhenWhenExcludeJobPublishersIsNull() {
        excludeJobPublishers = null;

        assertThatThrownBy(() -> new SearchFilters(remoteWork, country, excludeJobPublishers))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Exclude job publishers cannot be null.");
    }

    @Test
    void shouldReturnFalseWhenNotEquals() {
        SearchFilters searchFilters = new SearchFilters(remoteWork, country, excludeJobPublishers);

        remoteWork = false;
        country = new Country("US");
        excludeJobPublishers = new ExcludeJobPublishers(Set.of("publisher4", "publisher5"));

        SearchFilters searchFilters2 = new SearchFilters(remoteWork, country, excludeJobPublishers);

        assertThat(searchFilters.equals(searchFilters2)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenEquals() {
        SearchFilters searchFilters = new SearchFilters(remoteWork, country, excludeJobPublishers);

        SearchFilters searchFilters2 = new SearchFilters(remoteWork, country, excludeJobPublishers);

        assertThat(searchFilters.equals(searchFilters2)).isTrue();
    }
}