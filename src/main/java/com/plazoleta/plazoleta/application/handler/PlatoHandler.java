package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.EstadoPlatoRequest;
import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.application.mapper.IPlatoCreateRequestMapper;
import com.plazoleta.plazoleta.application.mapper.IPlatoResponseMapper;
import com.plazoleta.plazoleta.application.mapper.IPlatoUpdateRequestMapper;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatoHandler implements IPlatoHandler {
    private final IPlatoServicePort platoServicePort;
    private final IPlatoResponseMapper platoResponseMapper;
    private final IPlatoCreateRequestMapper platoCreateRequestMapper;
    private final IPlatoUpdateRequestMapper platoUpdateRequestMapper;
    @Override
    public void crearPlato(PlatoCreateRequestDto platoRequestDto) {
        // Implementación de la lógica para crear un plato
        Plato plato = platoCreateRequestMapper.toDomain(platoRequestDto);
        plato.setActivo(true); // Asignar el estado activo por defecto
        platoServicePort.crearPlato(plato);
    }

    @Override
    public PlatoResponseDto actualizarPlato(PlatoUpdateRequestDto platoRequestDto, Long idPlato) {
        // Implementación de la lógica para actualizar un plato
        Plato plato = platoUpdateRequestMapper.toDomain(platoRequestDto);
        return platoResponseMapper.toResponse(
                platoServicePort.actualizarPlato(plato, idPlato)
        );
    }
    @Override
    public PlatoResponseDto obtenerPlatoPorId(Long idPlato) {
        // Implementación de la lógica para obtener un plato por su ID
        Plato plato = platoServicePort.obtenerPlatoPorId(idPlato);
        return platoResponseMapper.toResponse(plato);
    }
    @Override
    public PlatoResponseDto habilitarDeshabilitarPlato(Long idPlato, EstadoPlatoRequest estadoPlatoRequest) {
        Plato plato = platoServicePort.habilitarDeshabilitarPlato(idPlato, estadoPlatoRequest.isEstado());
        return platoResponseMapper.toResponse(plato);
    }

    @Override
    public List<PlatoResponseDto> listarPlatosPorRestaurante(Long idRestaurante, int page, int size, String categoria) {
        return platoResponseMapper.toResponseList(
                platoServicePort.listarPlatosPorRestaurante(idRestaurante, page, size, categoria)
        );
    }
}
