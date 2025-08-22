package com.flashvagas.api.admin_api.application.scheduler.send_message;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.flashvagas.api.admin_api.application.service.plan_message.PlanMessageService;

@Component
public class SendMessageScheduler {
    private final PlanMessageService startService;
    private final PlanMessageService turboService;

    public SendMessageScheduler(
            @Qualifier("StartMessageServiceImpl") PlanMessageService startService,
            @Qualifier("TurboMessageServiceImpl") PlanMessageService turboService) {
        this.startService = startService;
        this.turboService = turboService;
    }
    /*
     * ┌───────────── segundo (0-59)
     * │ ┌───────────── minuto (0-59)
     * │ │ ┌───────────── hora (0-23)
     * │ │ │ ┌───────────── dia do mês (1-31)
     * │ │ │ │ ┌───────────── mês (1-12 ou JAN-DEC)
     * │ │ │ │ │ ┌───────────── dia da semana (0-6 ou SUN-SAT)
     */

    @Scheduled(cron = "0 0 9 * * *")
    @Scheduled(cron = "0 0 18 * * *")
    public void sendStartMessages() throws Exception {
        startService.sendMessages();
    }

    @Scheduled(cron = "0 0 7 * * *")
    @Scheduled(cron = "0 0 12 * * *")
    @Scheduled(cron = "0 0 16 * * *")
    @Scheduled(cron = "0 0 20 * * *")
    public void sendTurboMessages() throws Exception {
        turboService.sendMessages();
    }
}