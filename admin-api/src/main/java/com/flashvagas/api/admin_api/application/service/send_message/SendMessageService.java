package com.flashvagas.api.admin_api.application.service.send_message;

public interface SendMessageService {
    void sendEssentialsMessages() throws Exception;

    void sendTurboMessages() throws Exception;

    void sendAllMessages() throws Exception;
}
