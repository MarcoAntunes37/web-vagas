package com.webvagas.api.webvagas_api.persistence.user_preferences;

import org.junit.jupiter.api.Test;

import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

public class UserPreferencesEntityTest {
    @Test
    void shouldSyncKeywordsWithKeywordsDb() {
        UserPreferencesEntity userPreferencesEntity = new UserPreferencesEntity();

        String keyword = "test";

        Keywords keywords = new Keywords(keyword);

        userPreferencesEntity.setKeywords(keywords);

        assertThat(userPreferencesEntity.getKeywordsDb()).isEqualTo(keyword);
        assertThat(userPreferencesEntity.getKeywords().getValue()).isEqualTo(keyword);

        String keyword2 = "test2";

        userPreferencesEntity.setKeywordsDb(keyword2);

        assertThat(userPreferencesEntity.getKeywordsDb()).isEqualTo(keyword2);
        assertThat(userPreferencesEntity.getKeywords().getValue()).isEqualTo(keyword2);
    }

    @Test
    void shouldSyncEmploymentTypesWithEmploymentTypesDb() {
        UserPreferencesEntity userPreferencesEntity = new UserPreferencesEntity();

        EmploymentTypes employmentTypes = new EmploymentTypes(Set.of(EmploymentType.CONTRACTOR));

        userPreferencesEntity.setEmploymentTypes(employmentTypes);

        String expectedEmploymentTypeString = "CONTRACTOR";

        assertThat(userPreferencesEntity.getEmploymentTypesDb()).isEqualTo(expectedEmploymentTypeString);
        assertThat(userPreferencesEntity.getEmploymentTypes().contains(EmploymentType.CONTRACTOR)).isTrue();

        String expectedEmploymentTypeString2 = "FULLTIME,PARTTIME";

        userPreferencesEntity.setEmploymentTypesDb(expectedEmploymentTypeString2);

        assertThat(userPreferencesEntity.getEmploymentTypesDb()).isEqualTo(expectedEmploymentTypeString2);
        assertThat(userPreferencesEntity.getEmploymentTypes().contains(EmploymentType.FULLTIME)).isTrue();
        assertThat(userPreferencesEntity.getEmploymentTypes().contains(EmploymentType.PARTTIME)).isTrue();
    }

    @Test
    void shouldSyncExcludeJobPublishersAndDb() {
        UserPreferencesEntity entity = new UserPreferencesEntity();

        Set<String> publishersToExclude = Set.of("Indeed", "LinkedIn");

        ExcludeJobPublishers excludeJobPublishers = new ExcludeJobPublishers(publishersToExclude);

        entity.setExcludeJobPublishers(excludeJobPublishers);

        assertThat(entity.getExcludeJobPublishersDb()).isEqualTo("Indeed,LinkedIn");
        assertThat(entity.getExcludeJobPublishers().getTerms()).isEqualTo(publishersToExclude);

        Set<String> publishersToExclude2 = Set.of("Glassdoor");

        entity.setExcludeJobPublishersDb("Glassdoor");
        assertThat(entity.getExcludeJobPublishersDb()).isEqualTo("Glassdoor");
        assertThat(entity.getExcludeJobPublishers().getTerms()).isEqualTo(publishersToExclude2);
    }

    @Test
    void shouldHandleEmptyExcludeJobPublishersDb() {
        UserPreferencesEntity entity = new UserPreferencesEntity();

        entity.setExcludeJobPublishersDb("");

        assertThat(entity.getExcludeJobPublishers()).isNotNull();
        assertThat(entity.getExcludeJobPublishers().getTerms().isEmpty()).isTrue();
    }

    @Test
    void shouldHandleEmptyEmploymentTypesDb() {
        UserPreferencesEntity entity = new UserPreferencesEntity();
        entity.setEmploymentTypesDb(null);

        assertThat(entity.getEmploymentTypes()).isNotNull();
        assertThat(entity.getEmploymentTypes().values().isEmpty()).isTrue();
    }
}