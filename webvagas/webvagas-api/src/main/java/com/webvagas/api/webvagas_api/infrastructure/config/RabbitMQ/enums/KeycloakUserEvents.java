package com.webvagas.api.webvagas_api.infrastructure.config.RabbitMQ.enums;

public enum KeycloakUserEvents {
    USER_CASCADE_DELETE_PREFERENCES("on.user.delete.cascade.routing.key");

    private final String key;

    KeycloakUserEvents(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
