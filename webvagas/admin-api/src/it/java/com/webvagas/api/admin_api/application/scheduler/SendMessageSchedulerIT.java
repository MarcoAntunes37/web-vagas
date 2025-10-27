package com.webvagas.api.admin_api.application.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.webvagas.api.admin_api.application.scheduler.send_message.SendMessageScheduler;
import com.webvagas.api.admin_api.application.service.plan_message.PlanMessageService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class SendMessageSchedulerIT {
    
    @MockitoBean
    @Qualifier("StartMessageServiceImpl")
    PlanMessageService startService;

    @MockitoBean
    @Qualifier("TurboMessageServiceImpl")
    PlanMessageService turboService;

    @Autowired
    SendMessageScheduler scheduler;

    @Test
    void shouldCallStartServiceWhenSendStartMessagesIsTriggered() throws Exception {
        scheduler.sendStartMessages();
        verify(startService, times(1)).sendMessages();
    }

    @Test
    void shouldCallTurboServiceWhenSendTurboMessagesIsTriggered() throws Exception {
        scheduler.sendTurboMessages();
        verify(turboService, times(1)).sendMessages();
    }
}