package com.fiap.infomed.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "agendamento.queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}
