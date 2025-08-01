package com.plazoleta.plazoleta.infrastructure.out.jpa.repository;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.PlatoEntity;
import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlatoRepository extends JpaRepository<PlatoEntity, Long> {
    Page<PlatoEntity> findAllByIdRestauranteOrderByNombreAsc(Long idRestaurante, Pageable pageable);
    Page<PlatoEntity> findAllByIdRestauranteAndCategoriaOrderByNombreAsc(Long idRestaurante, Categoria categoria, Pageable pageable);
}
