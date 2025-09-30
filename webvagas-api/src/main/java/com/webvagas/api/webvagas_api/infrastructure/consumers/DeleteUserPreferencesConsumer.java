package com.webvagas.api.webvagas_api.infrastructure.consumers;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.webvagas.api.webvagas_api.application.service.user_preferences.UserPreferencesService;
import com.webvagas.api.webvagas_api.application.service.user_preferences.command.DeleteUserPreferencesCommand;


@Component
public class DeleteUserPreferencesConsumer {
    private UserPreferencesService userPreferencesService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(DeleteUserPreferencesConsumer.class);
    private static final String QUEUE_NAME = "on.user.delete.cascade.queue";
    public DeleteUserPreferencesConsumer(
            UserPreferencesService userPreferencesService) {
        this.userPreferencesService = userPreferencesService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void consumeDeleteUserPreferences(DeleteUserPreferencesCommand command) throws Exception {        
        int deletedLinesUserPreferences = 0;        
        try {
            deletedLinesUserPreferences = userPreferencesService
                    .deleteAllByUserId(command);            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("Deleted {} lines from user_preferences table", deletedLinesUserPreferences);
    }
}
