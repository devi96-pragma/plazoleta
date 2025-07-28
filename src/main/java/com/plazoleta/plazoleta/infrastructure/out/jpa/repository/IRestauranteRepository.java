package com.plazoleta.plazoleta.infrastructure.out.jpa.repository;

import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IRestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
}
