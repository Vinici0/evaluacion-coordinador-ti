package org.borja.springcloud.msvc.clients.msclients.services.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "${spring.kafka.topic.movements:movement-topic}", groupId = "${spring.kafka.consumer.group-id:client-service-group}")
    public void consumeMovementMessage(String message) {
        log.info("🎯 Mensaje recibido desde Kafka: {}", message);
        
        // Procesar el mensaje y enviar notificaciones
        processMovementNotification(message);
        
        // Aquí puedes agregar lógica adicional para procesar el mensaje
        // Por ejemplo: auditoría, integración con otros servicios, etc.
    }
    
    private void processMovementNotification(String message) {
        // Simular procesamiento del mensaje
        log.info("📧 ═══════════════════════════════════════════════════════════");
        log.info("📧 ✉️  NOTIFICACIÓN DE CORREO ELECTRÓNICO ENVIADA  ✉️");
        log.info("📧 ═══════════════════════════════════════════════════════════");
        log.info("📧 📤 Correo enviado exitosamente al cliente");
        log.info("📧 📋 Asunto: Notificación de Movimiento Bancario");
        log.info("📧 📝 Contenido: Se ha registrado un nuevo movimiento en su cuenta");
        log.info("📧 ⏰ Fecha de envío: {}", java.time.LocalDateTime.now());
        log.info("📧 ✅ Estado: ENVIADO CORRECTAMENTE");
        log.info("📧 ═══════════════════════════════════════════════════════════");
    }
}