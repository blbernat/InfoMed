package com.fiap.infomed.scheduler;

import com.fiap.infomed.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedRate = 120000)
    public void reportCurrentTime() {
        notificationService.verificarEEnviarNotificacoes();
    }
}
