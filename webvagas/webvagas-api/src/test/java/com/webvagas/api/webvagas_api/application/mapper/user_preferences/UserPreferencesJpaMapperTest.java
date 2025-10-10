package com.webvagas.api.webvagas_api.application.mapper.user_preferences;

import com.webvagas.api.webvagas_api.domain.entity.user_preferences.UserPreferences;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;
import com.webvagas.api.webvagas_api.domain.value_object.SearchFilters;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;
import com.webvagas.api.webvagas_api.persistence.user_preferences.UserPreferencesEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserPreferencesJpaMapperTest {

    private UserPreferencesJpaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserPreferencesJpaMapper.class);
    }

    @Test
    void shouldMapDomainToEntityCorrectly() {
        UUID id = UUID.randomUUID();

        UUID userId = UUID.randomUUID();

        Keywords keywords = new Keywords("java,spring,ddd");
        EmploymentTypes employmentTypes = new EmploymentTypes(
                Set.of(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR));
        SearchFilters filters = new SearchFilters(true, new Country("BR"),
                new ExcludeJobPublishers(Set.of("Indeed", "LinkedIn")));

        UserPreferences domain = new UserPreferences(
                new UserPreferencesId(id),
                new UserId(userId),
                keywords,
                employmentTypes,
                filters);

        UserPreferencesEntity entity = mapper.domainToEntity(domain);

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getUserId()).isEqualTo(userId);
        assertThat(entity.getKeywordsDb()).isEqualTo("java,spring,ddd");
        assertThat(entity.getEmploymentTypesDb()).contains("FULLTIME", "CONTRACTOR");
        assertThat(entity.getCountry()).isEqualTo("BR");
        assertThat(entity.getRemoteWork()).isTrue();
        assertThat(entity.getExcludeJobPublishersDb()).contains("Indeed", "LinkedIn");
    }

    @Test
    void shouldMapEntityToDomainCorrectly() {
        UUID id = UUID.randomUUID();

        UUID userId = UUID.randomUUID();

        UserPreferencesEntity entity = new UserPreferencesEntity();

        entity.setId(id);

        entity.setUserId(userId);

        entity.setKeywordsDb("java,spring");

        entity.setEmploymentTypesDb("FULLTIME,PARTTIME");

        entity.setCountry("BR");

        entity.setRemoteWork(false);

        entity.setExcludeJobPublishersDb("LinkedIn,Indeed");

        UserPreferences domain = mapper.entityToDomain(entity);

        assertThat(domain.getId().getValue()).isEqualTo(id);
        assertThat(domain.getUserId().getValue()).isEqualTo(userId);
        assertThat(domain.getKeywords().getValue()).isEqualTo("java,spring");

        Set<EmploymentType> employmentTypes = domain.getEmploymentTypes().values();
        assertThat(employmentTypes).containsExactlyInAnyOrder(EmploymentType.FULLTIME, EmploymentType.PARTTIME);

        SearchFilters filters = domain.getSearchFilters();
        assertThat(filters.getRemoteWork()).isFalse();
        assertThat(filters.getCountry().getValue()).isEqualTo("BR");
        assertThat(filters.getExcludeJobPublishers().getValue()).containsExactlyInAnyOrder("LinkedIn", "Indeed");
    }

    @Test
    void shouldMapEmploymentTypesToStringAndBack() {
        EmploymentTypes employmentTypes = new EmploymentTypes(
                Set.of(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR));
        String str = mapper.mapEmploymentTypesToString(employmentTypes);

        assertThat(str).contains("FULLTIME").contains("CONTRACTOR");

        EmploymentTypes parsed = mapper.mapStringToEmploymentTypes(str);
        assertThat(parsed.values()).containsExactlyInAnyOrder(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR);
    }

    @Test
    void shouldMapExcludeJobPublishersToStringAndBack() {
        ExcludeJobPublishers publishers = new ExcludeJobPublishers(Set.of("Indeed", "LinkedIn"));
        String str = mapper.mapExcludeJobPublishersToString(publishers);

        assertThat(str).contains("Indeed").contains("LinkedIn");

        ExcludeJobPublishers parsed = mapper.mapStringToExcludeJobPublishers(str);
        assertThat(parsed.getValue()).containsExactlyInAnyOrder("Indeed", "LinkedIn");
    }
}