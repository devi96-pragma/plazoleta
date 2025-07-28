package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.domain.model.Plato;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T14:29:11-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IPlatoResponseMapperImpl implements IPlatoResponseMapper {

    @Override
    public Plato toDomain(PlatoResponseDto platoResponseDto) {
        if ( platoResponseDto == null ) {
            return null;
        }

        Plato plato = new Plato();

        plato.setActivo( platoResponseDto.isActivo() );
        plato.setCategoria( platoResponseDto.getCategoria() );
        plato.setDescripcion( platoResponseDto.getDescripcion() );
        plato.setId( platoResponseDto.getId() );
        plato.setIdRestaurante( platoResponseDto.getIdRestaurante() );
        plato.setNombre( platoResponseDto.getNombre() );
        plato.setPrecio( platoResponseDto.getPrecio() );
        plato.setUrlImagen( platoResponseDto.getUrlImagen() );

        return plato;
    }

    @Override
    public PlatoResponseDto toResponse(Plato plato) {
        if ( plato == null ) {
            return null;
        }

        PlatoResponseDto platoResponseDto = new PlatoResponseDto();

        platoResponseDto.setActivo( plato.isActivo() );
        platoResponseDto.setCategoria( plato.getCategoria() );
        platoResponseDto.setDescripcion( plato.getDescripcion() );
        platoResponseDto.setId( plato.getId() );
        platoResponseDto.setIdRestaurante( plato.getIdRestaurante() );
        platoResponseDto.setNombre( plato.getNombre() );
        platoResponseDto.setPrecio( plato.getPrecio() );
        platoResponseDto.setUrlImagen( plato.getUrlImagen() );

        return platoResponseDto;
    }
}
