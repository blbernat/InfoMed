package com.fiap.infomed.consumer;

import com.fiap.infomed.config.RabbitConfig;
import com.fiap.infomed.dto.AgendamentoMessageDTO;
import com.fiap.infomed.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoConsumer {

    private final NotificationService service;

    public AgendamentoConsumer(NotificationService service) {
        this.service = service;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_CREATED)
    public void receiveCreated(AgendamentoMessageDTO agendamento) {
        service.saveNotification(agendamento);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_UPDATED)
    public void receiveUpdated(AgendamentoMessageDTO agendamento) {
        service.updateNotification(agendamento);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_DELETED)
    public void receiveDeleted(AgendamentoMessageDTO agendamento) {
        service.deleteNotification(agendamento);
    }
}
