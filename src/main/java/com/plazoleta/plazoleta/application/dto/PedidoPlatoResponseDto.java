package com.plazoleta.plazoleta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPlatoResponseDto {
    private Long idPlato;
    private String nombrePlato;
    private BigDecimal precioUnitario;
    private Integer cantidad;
}
