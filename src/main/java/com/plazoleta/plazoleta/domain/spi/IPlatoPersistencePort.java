package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Plato;

import java.util.Optional;

public interface IPlatoPersistencePort {
    void crearPlato(Plato plato);
    Plato actualizarPlato(Plato plato);
    Optional<Plato> findPlatoById(Long idPlato);
}
