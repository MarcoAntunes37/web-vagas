package com.webvagas.api.admin_api.domain.entity.message.dto;

public record SendMessageRequest(
                String to,
                String message) {
}
