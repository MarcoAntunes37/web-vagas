package com.webvagas.api.webvagas_api.api.controller.user_preferences;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvagas.api.webvagas_api.application.mapper.user_preferences.UserPreferencesApiMapper;
import com.webvagas.api.webvagas_api.application.service.user_preferences.UserPreferencesService;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.webvagas.api.webvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetRequest;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.webvagas.api.webvagas_api.domain.entity.user_preferences.dto.update.UserPreferencesUpdateRequest;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@Tag(name = "User preferences", description = "User preferences management")
@RequestMapping("api/v1/preferences/user")
public class UserPreferencesController {
        private final UserPreferencesService userPreferencesService;

        @Autowired
        private UserPreferencesApiMapper userPreferencesApiMapper;

        public UserPreferencesController(
                        UserPreferencesService userPreferencesService) {
                this.userPreferencesService = userPreferencesService;
        }

        @GetMapping("/{userId}")
        public ResponseEntity<UserPreferencesGetResponse> getByUserId(
                        @PathVariable UUID userId) {
                UserPreferencesGetRequest request = new UserPreferencesGetRequest(userId);

                GetUserPreferencesQuery query = userPreferencesApiMapper.getRequestToQuery(request);

                UserPreferencesGetResponse response = userPreferencesService.findByUserId(query);

                if (response == null) {
                        return ResponseEntity.notFound().build();
                }

                return ResponseEntity.ok(response);
        }

        @PostMapping("/{userId}")
        public ResponseEntity<UserPreferencesCreateResponse> create(
                        @PathVariable UUID userId,
                        @RequestBody UserPreferencesCreateRequest request) {
                CreateUserPreferencesCommand command = userPreferencesApiMapper
                                .createRequestToCommand(userId, request);

                UserPreferencesCreateResponse response = userPreferencesService
                                .create(command);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{userId}")
        public ResponseEntity<Void> update(
                        @PathVariable UUID userId,
                        @RequestBody UserPreferencesUpdateRequest request) {

                UpdateUserPreferencesCommand command = userPreferencesApiMapper
                                .updateRequestToCommand(userId, request);

                userPreferencesService.update(command);

                return ResponseEntity
                                .noContent()
                                .build();
        }
}