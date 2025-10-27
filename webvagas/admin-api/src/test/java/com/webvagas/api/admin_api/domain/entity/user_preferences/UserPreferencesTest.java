package com.webvagas.api.admin_api.domain.entity.user_preferences;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webvagas.api.admin_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.admin_api.domain.value_object.Country;
import com.webvagas.api.admin_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.admin_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.admin_api.domain.value_object.Keywords;
import com.webvagas.api.admin_api.domain.value_object.SearchFilters;
import com.webvagas.api.admin_api.domain.value_object.UserId;


public class UserPreferencesTest {
        private UserId userId;
        private Keywords keywords;
        private EmploymentTypes employmentTypes;
        private SearchFilters searchFilters;

        @BeforeEach
        void Setup() {
                userId = new UserId(UUID.randomUUID());
                keywords = new Keywords("keywords");
                employmentTypes = new EmploymentTypes(Set.of(EmploymentType.CONTRACTOR, EmploymentType.FULLTIME));
                searchFilters = new SearchFilters(true, new Country("BR"),
                                new ExcludeJobPublishers(Set.of("publisher1", "publisher2")));
        }

        @Test
        void shouldCreateUserPreferencesCorrectly() {
                UserPreferences prefs = new UserPreferences(userId, keywords, employmentTypes, searchFilters);

                assertThat(prefs.getUserId()).isEqualTo(userId);
                assertThat(prefs.getKeywords()).isEqualTo(keywords);
                assertThat(prefs.getEmploymentTypes()).isEqualTo(employmentTypes);
                assertThat(prefs.getSearchFilters()).isEqualTo(searchFilters);
        }

        @Test
        void shouldDelegateSearchFiltersAcessors() {
                UserPreferences prefs = new UserPreferences(userId, keywords, employmentTypes, searchFilters);

                assertThat(prefs.getSearchFiltersCountry()).isEqualTo(searchFilters.getCountry());
                assertThat(prefs.getSearchFiltersRemoteWork()).isEqualTo(searchFilters.getRemoteWork());
                assertThat(prefs.getSearchFiltersExcludeJobPublishers())
                                .isEqualTo(searchFilters.getExcludeJobPublishers());
        }
}