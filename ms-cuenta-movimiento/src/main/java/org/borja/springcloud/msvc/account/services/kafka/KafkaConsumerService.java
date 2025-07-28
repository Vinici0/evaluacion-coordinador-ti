package org.borja.springcloud.msvc.account.services.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "${kafka.topic.movement:movement-topic}", groupId = "account-service-group")
    public void consumeMovementMessage(String message) {
        log.info("🎯 Mensaje recibido desde Kafka: {}", message);
        // Aquí puedes agregar lógica adicional para procesar el mensaje
        // Por ejemplo: notificaciones, auditoría, integración con otros servicios, etc.
    }
}
    