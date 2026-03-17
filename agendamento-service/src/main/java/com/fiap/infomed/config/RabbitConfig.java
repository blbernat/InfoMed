package com.fiap.infomed.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "agendamento.exchange";
    public static final String QUEUE_CREATED = "agendamento.created.queue";
    public static final String QUEUE_UPDATED = "agendamento.updated.queue";
    public static final String QUEUE_DELETED = "agendamento.deleted.queue";
    public static final String ROUTING_KEY_CREATED = "agendamento.created";
    public static final String ROUTING_KEY_UPDATED = "agendamento.updated";
    public static final String ROUTING_KEY_DELETED = "agendamento.deletado";


    @Bean
    public Queue agendamentoCreatedQueue() {
        return new Queue(QUEUE_CREATED, true);
    }

    @Bean
    public Queue agendamentoUpdatedQueue() {
        return new Queue(QUEUE_UPDATED, true);
    }

    @Bean
    public Queue agendamentoDeletedQueue() {
        return new Queue(QUEUE_DELETED, true);
    }

    @Bean
    public TopicExchange agendamentoExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding agendamentoCreatedBinding(
            Queue agendamentoCreatedQueue,
            TopicExchange agendamentoExchange) {

        return BindingBuilder
                .bind(agendamentoCreatedQueue)
                .to(agendamentoExchange)
                .with(ROUTING_KEY_CREATED);
    }

    @Bean
    public Binding agendamentoUpdatedBinding(
            Queue agendamentoUpdatedQueue,
            TopicExchange agendamentoExchange) {

        return BindingBuilder
                .bind(agendamentoUpdatedQueue)
                .to(agendamentoExchange)
                .with(ROUTING_KEY_UPDATED);
    }

    @Bean
    public Binding agendamentoDeletedBinding(
            Queue agendamentoDeletedQueue,
            TopicExchange agendamentoExchange) {

        return BindingBuilder
                .bind(agendamentoDeletedQueue)
                .to(agendamentoExchange)
                .with(ROUTING_KEY_DELETED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
