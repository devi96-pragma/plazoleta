package com.plazoleta.plazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPlatoRequestDto {
    private Long idPlato;
    private int cantidad;
}
