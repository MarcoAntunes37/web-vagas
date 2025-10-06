package com.webvagas.api.webvagas_api.domain.value_objects;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.SearchFilters;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchFiltersTest {

    @Test
    void shouldCreateSearchFilters() {
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");
        
        SearchFilters searchFilters = new SearchFilters(
                true,
                new Country("BR"),
                new ExcludeJobPublishers(excludeJobPublishers));

        assertThat(searchFilters.getRemoteWork()).isEqualTo(true);
        assertThat(searchFilters.getCountry()).isEqualTo(new Country("BR"));
        assertThat(searchFilters.getExcludeJobPublishers())
                .isEqualTo(new ExcludeJobPublishers(excludeJobPublishers));
    }

    @Test
    void shouldReturnTrueWhenEquals(){
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");

        SearchFilters searchFilters1 = new SearchFilters(
                true,
                new Country("BR"),
                new ExcludeJobPublishers(excludeJobPublishers));

        SearchFilters searchFilters2 = new SearchFilters(
                true,
                new Country("BR"),
                new ExcludeJobPublishers(excludeJobPublishers));

        assertThat(searchFilters1.equals(searchFilters2)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotEquals(){
        Set<String> excludeJobPublishers = Set.of("publisher1", "publisher2", "publisher3");

        SearchFilters searchFilters1 = new SearchFilters(
                true,
                new Country("BR"),
                new ExcludeJobPublishers(excludeJobPublishers));

        SearchFilters searchFilters2 = new SearchFilters(
                false,
                new Country("BR"),
                new ExcludeJobPublishers(excludeJobPublishers));

        assertThat(searchFilters1.equals(searchFilters2)).isFalse();
    }
}