package com.plazoleta.plazoleta.domain.api;

import java.util.List;

public interface ITokenServicePort {
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    Long getUserIdFromToken(String token);
    Long getUserIdFromToken();
}
