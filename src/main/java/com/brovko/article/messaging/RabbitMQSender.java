package com.brovko.article.messaging;

import com.brovko.article.model.notification.EmailDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class RabbitMQSender {

    private AmqpTemplate rabbitTemplate;
    private Queue queue;

    @Autowired
    public RabbitMQSender(AmqpTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void send(EmailDetails emailDetails) {
        rabbitTemplate.convertAndSend(queue.getName(), emailDetails);
    }
}
