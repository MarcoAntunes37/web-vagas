package com.flashvagas.api.flashvagas_api.api.controller.user_preferences;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashvagas.api.flashvagas_api.application.mapper.user_preferences.UserPreferencesApiMapper;
import com.flashvagas.api.flashvagas_api.application.mapper.user_preferences.UserPreferencesCreateRequest;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.UserPreferencesService;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.command.CreateUserPreferencesCommand;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.command.UpdateUserPreferencesCommand;
import com.flashvagas.api.flashvagas_api.application.service.user_preferences.query.GetUserPreferencesQuery;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.create.UserPreferencesCreateResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetRequest;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.get.UserPreferencesGetResponse;
import com.flashvagas.api.flashvagas_api.domain.entity.user_preferences.dto.update.UserPreferencesUpdateRequest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController("UserPreferences Api")
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

        @PostMapping
        public ResponseEntity<UserPreferencesCreateResponse> create(
                        @RequestBody UserPreferencesCreateRequest request) {
                CreateUserPreferencesCommand command = userPreferencesApiMapper
                                .createRequestToCommand(request);

                UserPreferencesCreateResponse response = userPreferencesService
                                .create(command);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/")
        public ResponseEntity<Void> update(
                        @RequestBody UserPreferencesUpdateRequest request) {

                UpdateUserPreferencesCommand command = userPreferencesApiMapper
                                .updateRequestToCommand(request);

                userPreferencesService.update(command);

                return ResponseEntity
                                .noContent()
                                .build();
        }
}