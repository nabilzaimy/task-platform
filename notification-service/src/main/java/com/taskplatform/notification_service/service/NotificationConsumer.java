package com.taskplatform.notification_service.service;

import com.taskplatform.notification_service.config.RabbitMQConfig;
import com.taskplatform.notification_service.dto.TaskEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.TASK_CREATED_QUEUE)
    public void consumeTaskCreatedEvent(TaskEventDto event) {
        log.info("RECEIVED NOTIFICATION: New task created -> ID: {}, Title: '{}', Assignee ID: {}",
                event.getId(), event.getTitle(), event.getAssigneeId());
    }
}