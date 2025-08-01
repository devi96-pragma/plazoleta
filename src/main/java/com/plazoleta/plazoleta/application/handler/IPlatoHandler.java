package com.plazoleta.plazoleta.application.handler;

import com.plazoleta.plazoleta.application.dto.EstadoPlatoRequest;
import com.plazoleta.plazoleta.application.dto.PlatoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PlatoResponseDto;
import com.plazoleta.plazoleta.application.dto.PlatoUpdateRequestDto;
import com.plazoleta.plazoleta.domain.model.Categoria;

import java.util.List;

public interface IPlatoHandler {
    void crearPlato(PlatoCreateRequestDto platoRequestDto);
    PlatoResponseDto actualizarPlato(PlatoUpdateRequestDto platoRequestDto, Long idPlato);
    PlatoResponseDto obtenerPlatoPorId(Long idPlato);
    PlatoResponseDto habilitarDeshabilitarPlato(Long idPlato, EstadoPlatoRequest estadoRequest);
    List<PlatoResponseDto> listarPlatosPorRestaurante(Long idRestaurante, int page, int size, String categoria);
}
