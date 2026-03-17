package com.fiap.infomed.controller;

import com.fiap.infomed.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void testSendNotifications() {
        ResponseEntity<Void> response = notificationController.sendNotifications();

        verify(notificationService).verificarEEnviarNotificacoes();
        assertEquals(200, response.getStatusCodeValue());
    }
}
