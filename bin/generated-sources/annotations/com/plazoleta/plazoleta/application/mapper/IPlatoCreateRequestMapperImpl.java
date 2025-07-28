package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.domain.model.Plato;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T14:27:49-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IPlatoCreateRequestMapperImpl implements IPlatoCreateRequestMapper {

    @Override
    public PlatoCreateRequestDto toRequest(Plato plato) {
        if ( plato == null ) {
            return null;
        }

        PlatoCreateRequestDto platoCreateRequestDto = new PlatoCreateRequestDto();

        platoCreateRequestDto.setCategoria( plato.getCategoria() );
        platoCreateRequestDto.setDescripcion( plato.getDescripcion() );
        platoCreateRequestDto.setIdRestaurante( plato.getIdRestaurante() );
        platoCreateRequestDto.setNombre( plato.getNombre() );
        platoCreateRequestDto.setPrecio( plato.getPrecio() );
        platoCreateRequestDto.setUrlImagen( plato.getUrlImagen() );

        return platoCreateRequestDto;
    }

    @Override
    public Plato toDomain(PlatoCreateRequestDto platoCreateRequestDto) {
        if ( platoCreateRequestDto == null ) {
            return null;
        }

        Plato plato = new Plato();

        plato.setCategoria( platoCreateRequestDto.getCategoria() );
        plato.setDescripcion( platoCreateRequestDto.getDescripcion() );
        plato.setIdRestaurante( platoCreateRequestDto.getIdRestaurante() );
        plato.setNombre( platoCreateRequestDto.getNombre() );
        plato.setPrecio( platoCreateRequestDto.getPrecio() );
        plato.setUrlImagen( platoCreateRequestDto.getUrlImagen() );

        return plato;
    }
}
