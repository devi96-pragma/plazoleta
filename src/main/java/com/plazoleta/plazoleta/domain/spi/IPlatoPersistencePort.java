package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;

import java.util.List;
import java.util.Optional;

public interface IPlatoPersistencePort {
    void crearPlato(Plato plato);
    Plato actualizarPlato(Plato plato);
    Optional<Plato> findPlatoById(Long idPlato);
    List<Plato> listarPlatosPorRestaurante(Long idRestaurante, int page, int size);
    List<Plato> listarplatosPorRestauranteYCategoria(Long idRestaurante, Categoria categoria, int page, int size);
}
