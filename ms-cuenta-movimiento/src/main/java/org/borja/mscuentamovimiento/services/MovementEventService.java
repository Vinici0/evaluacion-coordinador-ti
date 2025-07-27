package org.borja.mscuentamovimiento.services;

import org.borja.mscuentamovimiento.dto.MovementEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MovementEventService {

    private static final Logger log = LoggerFactory.getLogger(MovementEventService.class);

    @Autowired
    private KafkaTemplate<String, MovementEventDto> kafkaTemplate;

    @Value("${kafka.topic.movement-events}")
    private String movementEventsTopic;

    public void publishMovementEvent(MovementEventDto eventDto) {
        try {
            log.info("📤 Enviando evento de movimiento al tópico '{}' para cliente: {}", 
                    movementEventsTopic, eventDto.getClientName());
            
            kafkaTemplate.send(movementEventsTopic, eventDto.getClientId().toString(), eventDto)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("✅ Evento enviado exitosamente para movimiento ID: {} - Offset: {}", 
                                    eventDto.getMovementId(), result.getRecordMetadata().offset());
                        } else {
                            log.error("❌ Error al enviar evento para movimiento ID: {}", 
                                    eventDto.getMovementId(), ex);
                        }
                    });
        } catch (Exception e) {
            log.error("💥 Excepción al publicar evento de movimiento: {}", e.getMessage(), e);
        }
    }
}
