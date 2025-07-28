package org.borja.springcloud.msvc.account.controllers;

import lombok.RequiredArgsConstructor;
import org.borja.springcloud.msvc.account.services.kafka.KafkaProducerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@RequiredArgsConstructor
public class KafkaTestRouter {

    private final KafkaProducerService kafkaProducerService;

    @Bean
    public RouterFunction<ServerResponse> kafkaTestRoutes() {
        return RouterFunctions
                .route(POST("/api/kafka/test").and(accept(MediaType.APPLICATION_JSON)),
                        request -> request.bodyToMono(String.class)
                                .doOnNext(message -> kafkaProducerService.sendMovementMessage("test-key", "TEST: " + message))
                                .then(ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(Mono.just("{\"status\":\"Message sent to Kafka successfully\"}"), String.class)))
                
                .andRoute(GET("/api/kafka/test"),
                        request -> {
                            kafkaProducerService.sendMovementMessage("test-key", "TEST: Mensaje de prueba desde GET endpoint");
                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(Mono.just("{\"status\":\"Test message sent to Kafka successfully\"}"), String.class);
                        });
    }
}
