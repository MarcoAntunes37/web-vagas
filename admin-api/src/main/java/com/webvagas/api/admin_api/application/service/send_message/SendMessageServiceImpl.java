package com.webvagas.api.admin_api.application.service.send_message;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.webvagas.api.admin_api.application.service.plan_message.PlanMessageService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {
    private final PlanMessageService startService;
    private final PlanMessageService turboService;

    public SendMessageServiceImpl(
            @Qualifier("StartMessageServiceImpl") PlanMessageService startService,
            @Qualifier("TurboMessageServiceImpl") PlanMessageService turboService) {
        this.startService = startService;
        this.turboService = turboService;
    }

    public void sendAllMessages() throws Exception {
        sendStartMessages();
        sendTurboMessages();
    }

    public void sendStartMessages() throws Exception {
        startService.sendMessages();
    }

    public void sendTurboMessages() throws Exception {
        turboService.sendMessages();
    }
}
