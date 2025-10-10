package com.webvagas.api.webvagas_api.application.mapper.user_preferences;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.UserPreferences;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.update.UserPreferencesUpdateRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.enums.EmploymentType;
import com.webvagas.api.webvagas_api.domain.value_object.Country;
import com.webvagas.api.webvagas_api.domain.value_object.EmploymentTypes;
import com.webvagas.api.webvagas_api.domain.value_object.ExcludeJobPublishers;
import com.webvagas.api.webvagas_api.domain.value_object.Keywords;
import com.webvagas.api.webvagas_api.domain.value_object.SearchFilters;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.domain.value_object.UserPreferencesId;

public class UserPreferencesApiMapperTest {

    private UserPreferencesApiMapper mapper;

    @BeforeEach
    void Setup() {
        mapper = Mappers.getMapper(UserPreferencesApiMapper.class);
    }

    @Test
    void shouldMapCreateRequestToCommandCorrectly() {
        UUID userIdUUID = UUID.randomUUID();

        UserId userId = new UserId(userIdUUID);

        UserPreferencesCreateRequest request = new UserPreferencesCreateRequest(userId.toString(), "java,spring,ddd",
                "FULLTIME,CONTRACTOR", true, "BR", "Indeed,LinkedIn");

        CreateUserPreferencesCommand command = mapper.createRequestToCommand(userIdUUID, request);

        assertThat(command).isNotNull();

        assertThat(command.userId()).isEqualTo(userId);
        assertThat(command.keywords()).isEqualTo(new Keywords("java,spring,ddd"));
        assertThat(command.employmentTypes())
                .isEqualTo(new EmploymentTypes(Set.of(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR)));
        assertThat(command.searchFilters()).isEqualTo(
                new SearchFilters(true, new Country("BR"), new ExcludeJobPublishers(Set.of("Indeed", "LinkedIn"))));
    }

    @Test
    void shouldMapDomainToApicreateResponseCorrectly() {
        UUID id = UUID.randomUUID();

        UUID userId = UUID.randomUUID();

        UserPreferencesId userPreferencesId = new UserPreferencesId(id);

        UserId userIdDomain = new UserId(userId);

        Keywords keywords = new Keywords("java,spring,ddd");

        EmploymentTypes employmentTypes = new EmploymentTypes(
                Set.of(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR));

        SearchFilters filters = new SearchFilters(true, new Country("BR"),
                new ExcludeJobPublishers(Set.of("Indeed", "LinkedIn")));

        UserPreferences domain = new UserPreferences(userPreferencesId, userIdDomain, keywords, employmentTypes,
                filters);

        UserPreferencesCreateResponse response = mapper.domainToApiCreateResponse(domain);

        assertThat(response).isNotNull();
        assertThat(keywords.getTerms()).isEqualTo(List.of("java", "spring", "ddd"));
        assertThat(employmentTypes.values()).containsExactlyInAnyOrder(EmploymentType.FULLTIME,
                EmploymentType.CONTRACTOR);
        assertThat(filters.getRemoteWork()).isEqualTo(true);
        assertThat(filters.getCountry().getValue()).isEqualTo("BR");
        assertThat(filters.getExcludeJobPublishers().getTerms()).containsExactlyInAnyOrder("Indeed", "LinkedIn");
    }

    @Test
    void shouldMapDomainToApiGetResponseCorrectly() {
        UUID id = UUID.randomUUID();

        UUID userId = UUID.randomUUID();

        UserPreferencesId userPreferencesId = new UserPreferencesId(id);

        UserId userIdDomain = new UserId(userId);

        Keywords keywords = new Keywords("java,spring,ddd");

        EmploymentTypes employmentTypes = new EmploymentTypes(
                Set.of(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR));

        SearchFilters filters = new SearchFilters(true, new Country("BR"),
                new ExcludeJobPublishers(Set.of("Indeed", "LinkedIn")));

        UserPreferences domain = new UserPreferences(userPreferencesId, userIdDomain, keywords, employmentTypes,
                filters);

        UserPreferencesGetResponse response = mapper.domainToApiGetResponse(domain);

        assertThat(response).isNotNull();
        assertThat(keywords.getTerms()).isEqualTo(List.of("java", "spring", "ddd"));
        assertThat(employmentTypes.values()).containsExactlyInAnyOrder(EmploymentType.FULLTIME,
                EmploymentType.CONTRACTOR);
        assertThat(filters.getRemoteWork()).isEqualTo(true);
        assertThat(filters.getCountry().getValue()).isEqualTo("BR");
        assertThat(filters.getExcludeJobPublishers().getTerms()).containsExactlyInAnyOrder("Indeed", "LinkedIn");
    }

    @Test
    void shouldMapGetRequestToQueryCorrectly() {
        UUID userId = UUID.randomUUID();

        UserPreferencesGetRequest request = new UserPreferencesGetRequest(userId);

        GetUserPreferencesQuery query = mapper.getRequestToQuery(request);

        assertThat(query).isNotNull();
        assertThat(query.userId()).isEqualTo(new UserId(userId));
    }

    @Test
    void shouldMapUpdateRequestToCommandCorrectly() {
        UUID userIdUUID = UUID.randomUUID();

        UserPreferencesUpdateRequest request = new UserPreferencesUpdateRequest(
                "java,spring,ddd", "FULLTIME,CONTRACTOR",
                "BR", true, "Indeed,LinkedIn");

        UpdateUserPreferencesCommand command = mapper.updateRequestToCommand(userIdUUID, request);

        assertThat(command).isNotNull();
        assertThat(command.keywords()).isEqualTo(new Keywords("java,spring,ddd"));
        assertThat(command.employmentTypes())
                .isEqualTo(new EmploymentTypes(Set.of(EmploymentType.FULLTIME, EmploymentType.CONTRACTOR)));
        assertThat(command.searchFilters()).isEqualTo(
                new SearchFilters(true, new Country("BR"), new ExcludeJobPublishers(Set.of("Indeed", "LinkedIn"))));
    }
}