package com.webvagas.api.admin_api.application.service.send_message;

public interface SendMessageService {
    void sendStartMessages() throws Exception;

    void sendTurboMessages() throws Exception;

    void sendAllMessages() throws Exception;
}
