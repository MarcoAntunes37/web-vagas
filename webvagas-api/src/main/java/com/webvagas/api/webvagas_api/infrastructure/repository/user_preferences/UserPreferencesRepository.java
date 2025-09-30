package com.webvagas.api.webvagas_api.infrastructure.repository.user_preferences;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webvagas.api.webvagas_api.persistence.user_preferences.UserPreferencesEntity;

import jakarta.transaction.Transactional;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, UUID> {
        @Query("FROM UserPreferencesEntity as UP " +
                        "WHERE UP.userId = :userId")
        UserPreferencesEntity findByUserId(UUID userId);

        @Query("DELETE FROM UserPreferencesEntity as UP " +
                        "WHERE UP.userId = :userId")
        @Modifying
        @Transactional
        int deleteAllByUserId(UUID userId);
}
