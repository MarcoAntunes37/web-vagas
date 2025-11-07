package com.webvagas.api.admin_api.api.controller.send_message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvagas.api.admin_api.application.service.send_message.SendMessageService;
import com.webvagas.api.admin_api.domain.entity.message.dto.SendMessageRequest;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1/send-message")
public class SendMessageController {
    private final SendMessageService sendMessageService;

    public SendMessageController(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @PostMapping("/")
    public ResponseEntity<String> sendMessage(
            @RequestBody SendMessageRequest request) throws Exception {
        sendMessageService.sendAllMessages();

        return ResponseEntity
                .ok()
                .build();
    }

}