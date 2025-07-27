package com.eureka.apigateway.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Configuration
public class GlobalErrorConfig {

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                Map<String, Object> map = super.getErrorAttributes(request, options);
                map.put("status", map.get("status"));
                map.put("message", map.get("message"));
                map.put("gateway", "api-gateway");
                map.put("timestamp", map.get("timestamp"));
                return map;
            }
        };
    }
}
