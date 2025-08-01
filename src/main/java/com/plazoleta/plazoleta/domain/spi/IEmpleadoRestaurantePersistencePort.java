package com.plazoleta.plazoleta.domain.spi;

import java.util.Optional;

public interface IEmpleadoRestaurantePersistencePort {
    void guardarEmpleadoRestaurante(Long idUsuario, Long idRestaurante);
    Optional<Long> obtenerIdRestaurantePorUsuario(Long idUsuario);
}
