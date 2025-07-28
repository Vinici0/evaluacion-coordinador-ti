package org.borja.springcloud.msvc.account.services.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    
    @Value("${kafka.topic.movement:movement-topic}")
    private String movementTopic;

    public void sendMovementMessage(String message) {
        log.info("Sending message to Kafka topic '{}': {}", movementTopic, message);
        
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(movementTopic, message);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent successfully to topic '{}' with offset: {}", 
                    movementTopic, result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send message to topic '{}': {}", movementTopic, ex.getMessage(), ex);
            }
        });
    }

    public void sendMovementMessage(String key, String message) {
        log.info("Sending message with key '{}' to Kafka topic '{}': {}", key, movementTopic, message);
        
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(movementTopic, key, message);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message with key '{}' sent successfully to topic '{}' with offset: {}", 
                    key, movementTopic, result.getRecordMetadata().offset());
            } else {
                log.error("Failed to send message with key '{}' to topic '{}': {}", 
                    key, movementTopic, ex.getMessage(), ex);
            }
        });
    }
}
