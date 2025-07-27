package org.borja.mscuentamovimiento.service;

import org.borja.mscuentamovimiento.dto.MovementEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MovementEventService {
    
    private static final Logger logger = LoggerFactory.getLogger(MovementEventService.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${spring.kafka.topic.movements}")
    private String movementsTopic;
    
    public MovementEventService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void publishMovementEvent(MovementEventDto movementEvent) {
        try {
            logger.info("📤 Enviando evento de movimiento a Kafka - Topic: {}, Movement ID: {}", 
                       movementsTopic, movementEvent.getMovementId());
            
            CompletableFuture<SendResult<String, Object>> future = 
                kafkaTemplate.send(movementsTopic, movementEvent.getMovementId().toString(), movementEvent);
            
            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    logger.info("✅ Evento de movimiento enviado exitosamente - Movement ID: {}, Offset: {}", 
                               movementEvent.getMovementId(), result.getRecordMetadata().offset());
                } else {
                    logger.error("❌ Error al enviar evento de movimiento - Movement ID: {}, Error: {}", 
                                movementEvent.getMovementId(), exception.getMessage());
                }
            });
            
        } catch (Exception e) {
            logger.error("❌ Error crítico al publicar evento de movimiento: {}", e.getMessage(), e);
        }
    }
}
