package com.plazoleta.plazoleta.infrastructure.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.plazoleta.plazoleta.infrastructure.constantes.Constantes.ConstantesHttp.BEARER;
import static com.plazoleta.plazoleta.infrastructure.constantes.Constantes.ConstantesHttp.HEADER_AUTHORIZATION;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor feignClientTokenInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() instanceof String token) {
                requestTemplate.header(HEADER_AUTHORIZATION, BEARER + token);
            }
        };
    }
}