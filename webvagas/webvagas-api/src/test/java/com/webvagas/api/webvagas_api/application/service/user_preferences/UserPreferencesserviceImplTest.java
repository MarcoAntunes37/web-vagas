package com.webvagas.api.webvagas_api.application.service.user_preferences;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.webvagas.api.webvagas_api.application.mapper.user_preferences.UserPreferencesApiMapper;
import com.webvagas.api.webvagas_api.application.mapper.user_preferences.UserPreferencesJpaMapper;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.DeleteUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.UserPreferences;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.infrastructure.repository.user_preferences.UserPreferencesRepository;
import com.webvagas.api.webvagas_api.persistence.user_preferences.UserPreferencesEntity;

public class UserPreferencesserviceImplTest {
    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @Mock
    private UserPreferencesJpaMapper userPreferencesJpaMapper;

    @Mock
    private UserPreferencesApiMapper userPreferencesApiMapper;

    private UserPreferencesServiceImpl userPreferencesService;

    @BeforeEach
    void Setup() {
        MockitoAnnotations.openMocks(this);
        userPreferencesService = new UserPreferencesServiceImpl(
                userPreferencesRepository,
                userPreferencesJpaMapper,
                userPreferencesApiMapper);
    }

    @Test
    void shouldReturnUserPreferencesResponseWhenCreationSuccess() {
        UUID uuid = UUID.randomUUID();

        CreateUserPreferencesCommand command = mock(CreateUserPreferencesCommand.class);

        UserPreferences domain = mock(UserPreferences.class);

        UserPreferencesEntity entity = mock(UserPreferencesEntity.class);

        UserPreferences savedDomain = mock(UserPreferences.class);

        UserPreferencesCreateResponse response = mock(UserPreferencesCreateResponse.class);

        when(userPreferencesJpaMapper.createCommandToDomain(command)).thenReturn(domain);

        when(userPreferencesRepository.findByUserId(any())).thenReturn(null);

        when(userPreferencesJpaMapper.domainToEntity(domain)).thenReturn(entity);

        when(userPreferencesJpaMapper.entityToDomain(entity)).thenReturn(savedDomain);

        when(userPreferencesApiMapper.domainToApiCreateResponse(savedDomain)).thenReturn(response);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(uuid);

        when(domain.getUserId()).thenReturn(userId);

        UserPreferencesCreateResponse result = userPreferencesService.create(command);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(response);
        Mockito.verify(userPreferencesRepository, Mockito.times(1)).save(entity);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenUserPreferencesAlreadyExists() {
        UUID uuid = UUID.randomUUID();

        CreateUserPreferencesCommand command = mock(CreateUserPreferencesCommand.class);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(uuid);

        when(command.userId()).thenReturn(userId);

        UserPreferences domain = mock(UserPreferences.class);

        when(domain.getUserId()).thenReturn(userId);

        when(userPreferencesJpaMapper.createCommandToDomain(command)).thenReturn(domain);

        when(userPreferencesRepository.findByUserId(uuid))
                .thenReturn(new UserPreferencesEntity());

        assertThatThrownBy(() -> userPreferencesService.create(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User preferences already exists");
    }

    @Test
    void shouldReturnResponseFindByUserIdWhenExists() {
        UUID id = UUID.randomUUID();

        GetUserPreferencesQuery query = mock(GetUserPreferencesQuery.class);

        UserPreferencesEntity entity = mock(UserPreferencesEntity.class);

        UserPreferences domain = mock(UserPreferences.class);

        UserPreferencesGetResponse response = mock(UserPreferencesGetResponse.class);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(id);

        when(query.userId()).thenReturn(userId);

        when(userPreferencesRepository.findByUserId(id)).thenReturn(entity);

        when(userPreferencesJpaMapper.entityToDomain(entity)).thenReturn(domain);

        when(userPreferencesApiMapper.domainToApiGetResponse(domain)).thenReturn(response);

        UserPreferencesGetResponse result = userPreferencesService.findByUserId(query);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldUpdateUserPreferenceswhenUserPreferencesExists() {
        UUID id = UUID.randomUUID();

        UpdateUserPreferencesCommand command = mock(UpdateUserPreferencesCommand.class);

        UserPreferencesEntity entity = mock(UserPreferencesEntity.class);

        UserPreferences existingDomain = mock(UserPreferences.class);

        UserPreferences newDomain = mock(UserPreferences.class);

        UserPreferencesEntity updatedEntity = mock(UserPreferencesEntity.class);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(id);

        when(command.userId()).thenReturn(userId);

        when(userPreferencesRepository.findByUserId(id)).thenReturn(entity);

        when(userPreferencesJpaMapper.entityToDomain(entity)).thenReturn(existingDomain);

        when(userPreferencesJpaMapper.updateCommandtoDomain(command)).thenReturn(newDomain);

        when(userPreferencesJpaMapper.domainToEntity(existingDomain)).thenReturn(updatedEntity);

        userPreferencesService.update(command);

        verify(existingDomain).update(newDomain);
        verify(userPreferencesRepository).save(updatedEntity);
    }

    @Test
    void shouldThrowExceptionWhenUserPreferencesNotExists() {
        UUID id = UUID.randomUUID();

        UpdateUserPreferencesCommand command = mock(UpdateUserPreferencesCommand.class);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(id);

        when(command.userId()).thenReturn(userId);

        when(userPreferencesRepository.findByUserId(id)).thenReturn(null);

        assertThatThrownBy(() -> userPreferencesService.update(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User preferences not found");
    }

    @Test
    void shouldReturnTheNumberOfDeletedLinesWhenDeleteAllByUserIdSuccess() {
        UUID id = UUID.randomUUID();

        DeleteUserPreferencesCommand command = mock(DeleteUserPreferencesCommand.class);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(id);

        when(command.userId()).thenReturn(userId);

        when(userPreferencesRepository.findByUserId(id)).thenReturn(mock(UserPreferencesEntity.class));

        when(userPreferencesRepository.deleteAllByUserId(id)).thenReturn(3);

        int deletedLines = userPreferencesService.deleteAllByUserId(command);

        assertEquals(3, deletedLines);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDeleteUserPreferencesNotExists() {
        UUID id = UUID.randomUUID();

        DeleteUserPreferencesCommand command = mock(DeleteUserPreferencesCommand.class);

        UserId userId = mock(UserId.class);

        when(userId.getValue()).thenReturn(id);

        when(command.userId()).thenReturn(userId);

        when(userPreferencesRepository.findByUserId(id)).thenReturn(null);

        assertThatThrownBy(() -> userPreferencesService.deleteAllByUserId(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User preferences not found");
    }
}