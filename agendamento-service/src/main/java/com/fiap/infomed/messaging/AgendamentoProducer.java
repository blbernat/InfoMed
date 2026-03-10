package com.fiap.infomed.messaging;

import com.fiap.infomed.config.RabbitConfig;
import com.fiap.infomed.entities.ConsultaEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoProducer {

    private final RabbitTemplate rabbitTemplate;

    public AgendamentoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAgendamentoCreated(ConsultaEntity agendamento) {

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                agendamento
        );

    }
}
