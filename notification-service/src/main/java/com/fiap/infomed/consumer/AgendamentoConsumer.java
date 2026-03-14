package com.fiap.infomed.consumer;

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

    @RabbitListener(queues = "agendamento.queue")
    public void receive(AgendamentoMessageDTO agendamento) {
        service.sendReminder(agendamento);
    }
}
