package com.plazoleta.plazoleta.application.mapper;

import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.domain.model.Plato;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T14:02:57-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class IPlatoUpdateRequestMapperImpl implements IPlatoUpdateRequestMapper {

    @Override
    public PlatoUpdateRequestDto toRequest(Plato plato) {
        if ( plato == null ) {
            return null;
        }

        PlatoUpdateRequestDto platoUpdateRequestDto = new PlatoUpdateRequestDto();

        platoUpdateRequestDto.setDescripcion( plato.getDescripcion() );
        platoUpdateRequestDto.setPrecio( plato.getPrecio() );

        return platoUpdateRequestDto;
    }

    @Override
    public Plato toDomain(PlatoUpdateRequestDto platoUpdateRequestDto) {
        if ( platoUpdateRequestDto == null ) {
            return null;
        }

        Plato plato = new Plato();

        plato.setDescripcion( platoUpdateRequestDto.getDescripcion() );
        plato.setPrecio( platoUpdateRequestDto.getPrecio() );

        return plato;
    }
}
