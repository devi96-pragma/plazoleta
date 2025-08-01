package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface IRestaurantePersistencePort {
    void saveRestaurante(Restaurante restaurante);
    Optional<Restaurante> findRestauranteById(Long idRestaurante);
    List<Restaurante> listarRestaurantesOrdenadosPorNombre(int page, int size);
}
