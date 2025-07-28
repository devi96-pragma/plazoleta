package com.plazoleta.plazoleta.infrastructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.infrastructure.out.jpa.entity.PlatoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T14:29:00-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IPlatoEntityMapperImpl implements IPlatoEntityMapper {

    @Override
    public PlatoEntity toEntity(Plato plato) {
        if ( plato == null ) {
            return null;
        }

        PlatoEntity platoEntity = new PlatoEntity();

        platoEntity.setActivo( plato.isActivo() );
        platoEntity.setCategoria( plato.getCategoria() );
        platoEntity.setDescripcion( plato.getDescripcion() );
        platoEntity.setId( plato.getId() );
        platoEntity.setIdRestaurante( plato.getIdRestaurante() );
        platoEntity.setNombre( plato.getNombre() );
        platoEntity.setPrecio( plato.getPrecio() );
        platoEntity.setUrlImagen( plato.getUrlImagen() );

        return platoEntity;
    }

    @Override
    public Plato toDomain(PlatoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Plato plato = new Plato();

        plato.setActivo( entity.isActivo() );
        plato.setCategoria( entity.getCategoria() );
        plato.setDescripcion( entity.getDescripcion() );
        plato.setId( entity.getId() );
        plato.setIdRestaurante( entity.getIdRestaurante() );
        plato.setNombre( entity.getNombre() );
        plato.setPrecio( entity.getPrecio() );
        plato.setUrlImagen( entity.getUrlImagen() );

        return plato;
    }
}
