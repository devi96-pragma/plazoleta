package com.plazoleta.plazoleta.infrastructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class PlatoJpaAdapter implements IPlatoPersistencePort {
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    @Override
    public void crearPlato(Plato plato) {
        platoRepository.save(platoEntityMapper.toEntity(plato));
    }

    @Override
    public Plato actualizarPlato(Plato plato) {
        return platoEntityMapper.toDomain(
                platoRepository.save(platoEntityMapper.toEntity(plato))
        );
    }

    @Override
    public Optional<Plato> findPlatoById(Long idPlato) {
        return platoRepository.findById(idPlato)
                .map(platoEntityMapper::toDomain);
    }

    @Override
    public List<Plato> listarPlatosPorRestaurante(Long idRestaurante, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return platoRepository
                .findAllByIdRestauranteAndIsActivoTrueOrderByNombreAsc(idRestaurante,pageable)
                .stream()
                .map(platoEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Plato> listarplatosPorRestauranteYCategoria(Long idRestaurante, Categoria categoria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return platoRepository
                .findAllByIdRestauranteAndCategoriaAndIsActivoTrueOrderByNombreAsc(idRestaurante, categoria, pageable)
                .stream()
                .map(platoEntityMapper::toDomain)
                .toList();
    }
}
