package com.plazoleta.plazoleta.infrastructure.out.jwt.adapter;

import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.infrastructure.out.jwt.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class JwtTokenAdapter implements ITokenServicePort {

    private final JwtConfig jwtConfig;

    public JwtTokenAdapter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                 SignatureException | IllegalArgumentException e) {
            return false;
        }
    }
    @Override
    public String getUsernameFromToken(String token) {
        return parseToken(token).getBody().getSubject();
    }
    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = parseToken(token).getBody();
        List<String> roles = (List<String>) claims.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecret().getBytes())
                .build()
                .parseClaimsJws(token);
    }

    @Override
    public Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof Map<?, ?> details) {
            Object id = details.get("id");
            if (id instanceof Long userId) {
                return userId;
            }
        }
        throw new IllegalStateException("No se pudo obtener el id del usuario autenticado");
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return parseToken(token).getBody().get("id", Long.class);
    }
}
