package com.flashvagas.api.admin_api.application.scheduler.send_message;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.flashvagas.api.admin_api.application.service.plan_message.PlanMessageService;

@Component
public class SendMessageScheduler {
    private final PlanMessageService essentialsService;
    private final PlanMessageService turboService;

    public SendMessageScheduler(
            @Qualifier("EssentialsMessageServiceImpl") PlanMessageService essentialsService,
            @Qualifier("TurboMessageServiceImpl") PlanMessageService turboService) {
        this.essentialsService = essentialsService;
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
    public void sendEssentialsMessages() throws Exception {
        essentialsService.sendMessages();
    }

    @Scheduled(cron = "0 0 7 * * *")
    @Scheduled(cron = "0 0 16 * * *")
    public void sendTurboMessages() throws Exception {
        turboService.sendMessages();
    }
}