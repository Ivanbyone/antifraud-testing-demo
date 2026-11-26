package io.ivanbyone.dictionary_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.ivanbyone.dictionary_service.entity.Outbox;
import io.ivanbyone.dictionary_service.entity.enums.OutboxStatus;
import io.ivanbyone.dictionary_service.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
@RequiredArgsConstructor 
public class OutboxScheduler {
    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRateString = "1000")
    public void process() {
        List<Outbox> messages = outboxRepository.findByStatus(OutboxStatus.WAIT);

        List<Long> queued = new ArrayList<>();
        List<Long> failed = new ArrayList<>();
        
        for (Outbox msg : messages) {
            try {
                rabbitTemplate.convertAndSend(msg.getQueueName(), msg.getMessage());
            } catch (AmqpException error) {
                failed.add(msg.getId());
                log.error("Ошибка при отправке сообщения в RabbitMQ", error);
            }

            queued.add(msg.getId());
        }

        if (!failed.isEmpty()) {
            outboxRepository.updateStatuses(OutboxStatus.FAILED, failed);
        }

        if (!queued.isEmpty()) {
            outboxRepository.updateStatuses(OutboxStatus.SENT, queued);
        }
    }
}
