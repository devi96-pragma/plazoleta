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
public class PlatoUpdateRequestDto {
    private float precio;
    private String descripcion;
}
