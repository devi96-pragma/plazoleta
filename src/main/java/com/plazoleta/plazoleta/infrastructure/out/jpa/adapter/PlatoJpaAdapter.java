package com.plazoleta.plazoleta.infrastructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IPlatoRepository;
import lombok.RequiredArgsConstructor;
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
}
