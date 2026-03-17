package com.fiap.infomed.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_CREATED = "agendamento.created.queue";
    public static final String QUEUE_UPDATED = "agendamento.updated.queue";
    public static final String QUEUE_DELETED = "agendamento.deleted.queue";

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
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
