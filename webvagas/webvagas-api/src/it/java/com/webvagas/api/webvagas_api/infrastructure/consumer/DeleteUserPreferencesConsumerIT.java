package com.webvagas.api.webvagas_api.infrastructure.consumer;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.webvagas.api.webvagas_api.application.service.user_preferences.UserPreferencesService;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.DeleteUserPreferencesCommand;
import com.webvagas.api.webvagas_api.config.RabbitTestConfig;
import com.webvagas.api.webvagas_api.domain.value_object.UserId;
import com.webvagas.api.webvagas_api.infrastructure.config.RabbitMQ.enums.KeycloakUserEvents;
import com.webvagas.api.webvagas_api.infrastructure.consumers.DeleteUserPreferencesConsumer;

@Testcontainers
@SpringBootTest(classes = { DeleteUserPreferencesConsumer.class, RabbitTestConfig.class }, properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," +
                "logging.level.org.springframework.amqp=DEBUG," +
                "logging.level.org.springframework.messaging=DEBUG" })
class DeleteUserPreferencesConsumerIT {
    @SuppressWarnings("resource")
    @Container
    static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:4.0-management-alpine")
            .withExposedPorts(5672);

    @DynamicPropertySource
    static void configureRabbitProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbit::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbit::getAdminPassword);
    }

    @Mock
    private RabbitTemplate rabbitTemplate;

    @MockitoBean
    private UserPreferencesService userPreferencesService;

    @BeforeEach
    void setupMocks() {
        reset(userPreferencesService);
    }

    @Test
    void shouldConsumeMessageAndDeleteUserPreferences() {
        DeleteUserPreferencesCommand command = new DeleteUserPreferencesCommand(new UserId(UUID.randomUUID()));

        when(userPreferencesService.deleteAllByUserId(command)).thenReturn(5);

        rabbitTemplate.convertAndSend(
                "keycloak.user.events",
                KeycloakUserEvents.USER_CASCADE_DELETE_PREFERENCES.getKey(),
                command);

        await()
                .pollInterval(Duration.ofMillis(200))
                .atMost(Duration.ofSeconds(15))
                .untilAsserted(() -> {
                    verify(userPreferencesService, times(1))
                            .deleteAllByUserId(command);
                });
    }
}