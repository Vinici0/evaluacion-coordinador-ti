package org.borja.msclientepersona.service;

import org.borja.msclientepersona.dto.MovementEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    @KafkaListener(topics = "${spring.kafka.topic.movements}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleMovementEvent(@Payload MovementEventDto movementEvent,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                   @Header(KafkaHeaders.OFFSET) long offset,
                                   Acknowledgment acknowledgment) {
        
        try {
            logger.info("🎧 TÓPICO RECIBIDO - Topic: {}, Partition: {}, Offset: {}", topic, partition, offset);
            logger.info("📨 Evento de movimiento recibido: {}", movementEvent);
            
            // Simular envío de email
            simulateEmailSending(movementEvent);
            
            // Confirmar que el mensaje fue procesado
            acknowledgment.acknowledge();
            logger.info("✅ Evento procesado exitosamente - Movement ID: {}", movementEvent.getMovementId());
            
        } catch (Exception e) {
            logger.error("❌ Error al procesar evento de movimiento: {}", e.getMessage(), e);
            // En un escenario real, aquí podrías decidir si hacer acknowledge o no
            acknowledgment.acknowledge();
        }
    }
    
    private void simulateEmailSending(MovementEventDto movementEvent) {
        logger.info("📧 SIMULANDO ENVÍO DE EMAIL:");
        logger.info("   📩 Para: {} <{}>", movementEvent.getClientName(), movementEvent.getClientEmail());
        logger.info("   📄 Asunto: Notificación de Movimiento - Cuenta {}", movementEvent.getAccountNumber());
        logger.info("   💰 Detalle: {} por ${} - Saldo actual: ${}", 
                   movementEvent.getMovementType(), 
                   Math.abs(movementEvent.getAmount()), 
                   movementEvent.getBalance());
        logger.info("   📅 Fecha: {}", movementEvent.getDate());
        logger.info("   ✉️ Email enviado exitosamente (SIMULADO)");
    }
}
