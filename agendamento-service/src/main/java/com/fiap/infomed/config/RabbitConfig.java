package com.fiap.infomed.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "agendamento.exchange";
    public static final String QUEUE = "agendamento.queue";
    public static final String ROUTING_KEY_CREATED = "agendamento.created";
    public static final String ROUTING_KEY_UPDATED = "agendamento.updated";

    @Bean
    public Queue agendamentoQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange agendamentoExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding agendamentoCreatedBinding(
            Queue appointmentQueue,
            TopicExchange appointmentExchange) {

        return BindingBuilder
                .bind(appointmentQueue)
                .to(appointmentExchange)
                .with(ROUTING_KEY_CREATED);
    }

    @Bean
    public Binding agendamentoUpdatedBinding(
            Queue appointmentQueue,
            TopicExchange appointmentExchange) {

        return BindingBuilder
                .bind(appointmentQueue)
                .to(appointmentExchange)
                .with(ROUTING_KEY_UPDATED);
    }
}
