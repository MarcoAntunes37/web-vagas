package com.webvagas.api.webvagas_api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RabbitTestConfig {

    @Bean
    Queue deleteAllQueue() {
        return new Queue("delete.all.queue", true);
    }
}