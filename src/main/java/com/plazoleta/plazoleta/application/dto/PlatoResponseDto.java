package com.plazoleta.plazoleta.application.dto;

import com.plazoleta.plazoleta.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlatoResponseDto {
    private Long id;
    private String nombre;
    private float precio;
    private String descripcion;
    private String urlImagen;
    private Categoria categoria;
    private boolean isActivo;
    private Long idRestaurante;
}
