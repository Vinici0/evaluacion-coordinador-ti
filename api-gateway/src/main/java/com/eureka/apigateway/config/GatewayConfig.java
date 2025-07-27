package com.eureka.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Ruta para ms-personas (clientes)
                .route("ms-personas", r -> r
                        .path("/api/clientes/**")
                        .filters(f -> f
                                .stripPrefix(1) // Remueve /api
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Gateway", "api-gateway")
                                .retry(3)) // Reintentos en caso de falla
                        .uri("lb://ms-personas"))
                
                // Ruta para ms-cuenta-movimiento (cuentas)
                .route("ms-cuenta-movimiento-cuentas", r -> r
                        .path("/api/cuentas/**")
                        .filters(f -> f
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Gateway", "api-gateway")
                                .retry(3)) // Sin strip prefix
                        .uri("lb://ms-cuenta-movimiento"))
                
                // Ruta para movimientos
                .route("ms-cuenta-movimiento-movimientos", r -> r
                        .path("/api/movimientos/**")
                        .filters(f -> f
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Gateway", "api-gateway")
                                .retry(3)) // Sin strip prefix
                        .uri("lb://ms-cuenta-movimiento"))
                
                // Ruta para reportes (nueva funcionalidad común)
                .route("ms-cuenta-movimiento-reportes", r -> r
                        .path("/api/reportes/**")
                        .filters(f -> f
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .addResponseHeader("X-Response-Gateway", "api-gateway")
                                .retry(3))
                        .uri("lb://ms-cuenta-movimiento"))
                
                .build();
    }
}
