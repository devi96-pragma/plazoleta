package com.plazoleta.plazoleta.infrastructure.out.jwt.filter;

import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.infrastructure.out.jwt.adapter.JwtTokenAdapter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Profile("!test")
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenAdapter jwtTokenAdapter;

    public JwtAuthenticationFilter(JwtTokenAdapter jwtTokenAdapter) {
        this.jwtTokenAdapter = jwtTokenAdapter;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtTokenAdapter.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtTokenAdapter.validateToken(token)) {
                    var authorities = jwtTokenAdapter.getAuthorities(token);
                    Long userId = jwtTokenAdapter.getUserIdFromToken(token);

                    // Crea la autenticación
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, token, authorities
                    );

                    //Guarda el ID en los detalles
                    Map<String, Object> detalles = new HashMap<>();
                    detalles.put("id", userId);
                    auth.setDetails(detalles);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
