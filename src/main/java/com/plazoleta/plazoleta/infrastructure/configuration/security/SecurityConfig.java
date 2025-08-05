package com.plazoleta.plazoleta.infrastructure.configuration.security;

import com.plazoleta.plazoleta.infrastructure.out.jwt.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml"
                        ).permitAll()
                        .requestMatchers(POST, "/restaurantes").hasRole("ADMINISTRADOR")
                        .requestMatchers(GET, "/platos/restaurante/*").hasRole("CLIENTE")//listarPlatosPorRestaurante
                        .requestMatchers(GET,"/platos/*").hasRole("EMPLEADO")//findPlatoById
                        .requestMatchers(PATCH,"/platos/*").hasRole("PROPIETARIO")//actualizarPlato deshabilitar habilitar plato
                        .requestMatchers(POST,"/platos").hasRole("PROPIETARIO")//crearPlato
                        .requestMatchers(GET, "/platos/restaurante/*").hasRole("CLIENTE")//Listar los platos de un restaurante
                        .requestMatchers(GET, "/restaurantes").hasRole("CLIENTE")//Listar los restaurante x clientes
                        .requestMatchers(POST, "/pedidos").hasRole("CLIENTE")
                        .requestMatchers(GET, "/pedidos").hasRole("EMPLEADO")
                        .requestMatchers(PATCH, "/pedidos/*").hasRole("EMPLEADO")
                        .requestMatchers(GET, "/pedidos/*/notificar-listo").hasRole("EMPLEADO")
                        .requestMatchers(PATCH, "/pedidos/*/entregar").hasRole("EMPLEADO")
                        .requestMatchers(PATCH, "/pedidos/*/cancelar").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
