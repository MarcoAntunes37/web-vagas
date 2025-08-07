package com.flashvagas.api.flashvagas_api.infrastructure.config.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flashvagas.api.flashvagas_api.infrastructure.config.RabbitMQ.enums.KeycloakUserEvents;

@Configuration
public class OnUserDeleteCascade {
    private static final String EXCHANGE_NAME = "keycloak.user.events";
    private static final String QUEUE_NAME = "on.user.delete.cascade.queue";

    @Bean
    Queue onUserDeleteCascadeUserPreferencesQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    TopicExchange onUserDeleteCascadeUserPreferencesExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding bindOnUserDeleteCascadeUserPreferencesAndExchange(
            Queue onUserDeleteCascadeUserPreferencesQueue,
            TopicExchange onUserDeleteCascadeUserPreferencesExchange) {
        return BindingBuilder
                .bind(onUserDeleteCascadeUserPreferencesQueue)
                .to(onUserDeleteCascadeUserPreferencesExchange)
                .with(KeycloakUserEvents.USER_CASCADE_DELETE_PREFERENCES.getKey());
    }
}