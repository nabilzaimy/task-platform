package com.taskplatform.task_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "task-events-exchange";
    public static final String TASK_CREATED_QUEUE = "task-created-queue";
    public static final String TASK_CREATED_ROUTING_KEY = "task.created";

    @Bean
    public TopicExchange taskEventsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue taskCreatedQueue() {
        return new Queue(TASK_CREATED_QUEUE);
    }

    @Bean
    public Binding taskCreatedBinding(Queue taskCreatedQueue, TopicExchange taskEventsExchange) {
        return BindingBuilder.bind(taskCreatedQueue)
                .to(taskEventsExchange)
                .with(TASK_CREATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}