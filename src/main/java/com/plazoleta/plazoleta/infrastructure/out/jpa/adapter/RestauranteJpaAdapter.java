package com.plazoleta.plazoleta.infrastructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    @Override
    public void saveRestaurante(Restaurante restaurante) {
        restauranteRepository.save(restauranteEntityMapper.toEntity(restaurante));
    }
    @Override
    public Optional<Restaurante> findRestauranteById(Long idRestaurante) {
        return  restauranteRepository.findById(idRestaurante)
                .map(restauranteEntityMapper::toDomain);
    }
}
