package com.webvagas.api.webvagas_api.domain.entity.user_preferences;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;
import com.webvagas.api.webvagas_api.domain.value_object.SearchFilters;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;

public class UserPreferencesTest {
        private UserPreferencesId id;
        private UserId userId;
        private Keywords keywords;
        private EmploymentTypes employmentTypes;
        private SearchFilters searchFilters;

        @BeforeEach
        void Setup() {
                id = new UserPreferencesId(UUID.randomUUID());
                userId = new UserId(UUID.randomUUID());
                keywords = new Keywords("keywords");
                employmentTypes = new EmploymentTypes(Set.of(EmploymentType.FULLTIME));
                searchFilters = new SearchFilters(true, new Country("BR"),
                                new ExcludeJobPublishers(Set.of("publisher1", "publisher2")));
        }

        @Test
        void shouldCreateUserPreferencesCorrectly() {
                UserPreferences prefs = new UserPreferences(id, userId, keywords, employmentTypes, searchFilters);

                assertThat(prefs.getId()).isEqualTo(id);
                assertThat(prefs.getUserId()).isEqualTo(userId);
                assertThat(prefs.getKeywords()).isEqualTo(keywords);
                assertThat(prefs.getEmploymentTypes()).isEqualTo(employmentTypes);
                assertThat(prefs.getSearchFilters()).isEqualTo(searchFilters);
        }

        @Test
        void shouldUpdateKeywordsEmploymentAndSearchFilters() {
                UserPreferences prefs = new UserPreferences(
                                id, userId, keywords, employmentTypes, searchFilters);

                Keywords keywords2 = new Keywords("keywords2");

                EmploymentTypes employmentTypes2 = new EmploymentTypes(Set.of(
                                EmploymentType.PARTTIME));

                SearchFilters searchFilters2 = new SearchFilters(false, new Country("US"),
                                new ExcludeJobPublishers(Set.of("publisher3", "publisher4")));

                UserPreferences updated = new UserPreferences(id, userId, keywords2, employmentTypes2, searchFilters2);

                prefs.update(updated);

                assertThat(prefs.getKeywords()).isEqualTo(keywords2);
                assertThat(prefs.getEmploymentTypes()).isEqualTo(employmentTypes2);
                assertThat(prefs.getSearchFilters()).isEqualTo(searchFilters2);
        }

        @Test
        void shouldDelegateSearchFiltersAcessors() {
                UserPreferences prefs = new UserPreferences(id, userId, keywords, employmentTypes, searchFilters);

                assertThat(prefs.getSearchFiltersCountry()).isEqualTo(searchFilters.getCountry());
                assertThat(prefs.getSearchFiltersRemoteWork()).isEqualTo(searchFilters.getRemoteWork());
                assertThat(prefs.getSearchFiltersExcludeJobPublishers())
                                .isEqualTo(searchFilters.getExcludeJobPublishers());
        }
}