package com.plazoleta.plazoleta.application.dto;

import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDto {
    private Long id;
    private Long idRestaurante;
    private EstadoPedido estado;
    private LocalDateTime fechaCreacion;
    private List<PedidoPlatoResponseDto> platos;
    private BigDecimal precioTotal;
}
