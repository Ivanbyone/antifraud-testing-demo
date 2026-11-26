package io.ivanbyone.dictionary_service.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration 
public class RabbitConfiguration {

    @Value("${rabbit.queues.black-queue-to}")
    private String blackOutbox;

    
    @Bean 
    public Queue blackOutboxQueue() {
        return new Queue(blackOutbox);
    }
}
