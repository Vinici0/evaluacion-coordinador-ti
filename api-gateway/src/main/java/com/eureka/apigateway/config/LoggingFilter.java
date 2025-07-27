package com.eureka.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        logger.info("Gateway Request: {} {} from {}:{}",
                request.getMethod(),
                request.getURI(),
                request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress() : "unknown",
                request.getRemoteAddress() != null ? request.getRemoteAddress().getPort() : "unknown");
        
        return chain.filter(exchange).doFinally(signalType -> {
            logger.info("Gateway Response: {} - Status: {}",
                    request.getURI(),
                    exchange.getResponse().getStatusCode());
        });
    }

    @Override
    public int getOrder() {
        return -1; // Execute before other filters
    }
}
