package com.webvagas.api.webvagas_api.application.service.user_preferences;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webvagas.api.webvagas_api.application.mapper.user_preferences.UserPreferencesApiMapper;
import com.webvagas.api.webvagas_api.application.mapper.user_preferences.UserPreferencesJpaMapper;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.DeleteUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.UserPreferences;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.webvagas_api.infrastructure.repository.user_preferences.UserPreferencesRepository;
import com.webvagas.api.webvagas_api.persistence.user_preferences.UserPreferencesEntity;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private UserPreferencesJpaMapper userPreferencesJpaMapper;

    @Autowired
    private UserPreferencesApiMapper userPreferencesApiMapper;

    public UserPreferencesServiceImpl(UserPreferencesRepository userPreferencesRepository) {
        this.userPreferencesRepository = userPreferencesRepository;
    }

    @Transactional
    public UserPreferencesCreateResponse create(CreateUserPreferencesCommand command) {
        UserPreferences domain = userPreferencesJpaMapper.createCommandToDomain(command);

        if (userPreferencesRepository.findByUserId(domain.getUserId().getValue()) != null) {
            throw new IllegalArgumentException("User preferences already exists");
        }

        UserPreferencesEntity entity = userPreferencesJpaMapper.domainToEntity(domain);

        userPreferencesRepository.save(entity);

        UserPreferences savedDomain = userPreferencesJpaMapper.entityToDomain(entity);

        return userPreferencesApiMapper.domainToApiCreateResponse(savedDomain);
    }

    public UserPreferencesGetResponse findByUserId(GetUserPreferencesQuery query) {
        UUID uuidUserId = query.userId().getValue();

        UserPreferencesEntity entity = userPreferencesRepository.findByUserId(uuidUserId);

        UserPreferences domain = userPreferencesJpaMapper.entityToDomain(entity);

        return userPreferencesApiMapper.domainToApiGetResponse(domain);
    }

    public void update(UpdateUserPreferencesCommand command) {
        UUID uuidUserId = command.userId().getValue();

        UserPreferencesEntity entity = userPreferencesRepository.findByUserId(uuidUserId);

        if (entity == null) {
            throw new IllegalArgumentException("User preferences not found");
        }

        UserPreferences userPreferencesExistent = userPreferencesJpaMapper.entityToDomain(entity);

        UserPreferences newUserPreferences = userPreferencesJpaMapper.updateCommandtoDomain(command);

        userPreferencesExistent.update(newUserPreferences);

        UserPreferencesEntity updatedEntity = userPreferencesJpaMapper
                .domainToEntity(userPreferencesExistent);

        userPreferencesRepository.save(updatedEntity);
    }

    public int deleteAllByUserId(DeleteUserPreferencesCommand command) {
        UUID uuidUserId = command.userId().getValue();

        if (userPreferencesRepository.findByUserId(uuidUserId) == null) {
            throw new IllegalArgumentException("User preferences not found");
        }

        int deletedLines = 0;

        try {
            deletedLines = userPreferencesRepository.deleteAllByUserId(uuidUserId);
        } catch (Exception e) {
            throw new PersistenceException("Error deleting user preferences", e);
        }

        return deletedLines;
    }
}
