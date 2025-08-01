package com.plazoleta.plazoleta.infrastructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

    @Override
    public List<Restaurante> listarRestaurantesOrdenadosPorNombre(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return restauranteRepository
                .findAllByOrderByNombreAsc(pageable)
                .stream()
                .map(restauranteEntityMapper::toDomain)
                .toList();
    }
}
