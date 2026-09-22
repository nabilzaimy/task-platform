package com.taskplatform.notification_service.config;

import com.taskplatform.notification_service.dto.TaskEventDto;
import com.taskplatform.notification_service.dto.TaskResponse; // Ensure this points to your local DTO class
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String TASK_EVENTS_EXCHANGE = "task-events-exchange";
    public static final String TASK_CREATED_QUEUE = "task-created-queue";
    public static final String ROUTING_KEY_TASK_CREATED = "task.created";

    // 1. Deklarasi Queue
    @Bean
    public Queue taskCreatedQueue() {
        return new Queue(TASK_CREATED_QUEUE, true);
    }

    // 2. Deklarasi Exchange
    @Bean
    public TopicExchange taskEventsExchange() {
        return new TopicExchange(TASK_EVENTS_EXCHANGE);
    }

    // 3. Deklarasi Binding (Ikat Queue ke Exchange dengan Routing Key)
    @Bean
    public Binding binding(Queue taskCreatedQueue, TopicExchange taskEventsExchange) {
        return BindingBuilder.bind(taskCreatedQueue).to(taskEventsExchange).with(ROUTING_KEY_TASK_CREATED);
    }

    // 4. JSON Message Converter with Class Mapping
    @Bean
    public MessageConverter jsonMessageConverter() {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");

        // Map producer's TaskResponse to consumer's TaskEventDto
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.taskplatform.task_service.dto.TaskResponse", TaskEventDto.class);
        classMapper.setIdClassMapping(idClassMapping);

        converter.setClassMapper(classMapper);
        return converter;
    }
}