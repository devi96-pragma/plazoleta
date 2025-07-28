package com.plazoleta.plazoleta.infrastructure.out.jpa.repository;

import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlatoRepository extends JpaRepository<PlatoEntity, Long> {

}
