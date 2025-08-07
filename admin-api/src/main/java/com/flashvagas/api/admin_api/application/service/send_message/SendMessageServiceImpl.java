package com.flashvagas.api.admin_api.application.service.send_message;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.flashvagas.api.admin_api.application.service.plan_message.PlanMessageService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {
    private final PlanMessageService essentialsService;
    private final PlanMessageService turboService;

    public SendMessageServiceImpl(
            @Qualifier("EssentialsMessageServiceImpl") PlanMessageService essentialsService,
            @Qualifier("TurboMessageServiceImpl") PlanMessageService turboService) {
        this.essentialsService = essentialsService;
        this.turboService = turboService;
    }

    public void sendAllMessages() throws Exception {
        sendEssentialsMessages();
        sendTurboMessages();
    }

    public void sendEssentialsMessages() throws Exception {
        essentialsService.sendMessages();
    }

    public void sendTurboMessages() throws Exception {
        turboService.sendMessages();
    }
}
