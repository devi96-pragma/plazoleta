package com.plazoleta.plazoleta.infrastructure.input.rest;

import com.plazoleta.plazoleta.application.dto.PedidoCreateRequestDto;
import com.plazoleta.plazoleta.application.dto.PedidoResponseDto;
import com.plazoleta.plazoleta.application.handler.IPedidoHandler;
import com.plazoleta.plazoleta.domain.model.EstadoPedido;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final IPedidoHandler pedidoHandler;

    @PostMapping
    public ResponseEntity<Void> crearPedido(@RequestBody PedidoCreateRequestDto pedidoCreateRequestDto) {
        pedidoHandler.crearPedido(pedidoCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> obtenerListaPedidosPorEstado(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size,
            @RequestParam(required = true) EstadoPedido estado
    ){
        List<PedidoResponseDto> pedidos = pedidoHandler.obtenerListaPedidoPorEstado(page,size,estado);
        return ResponseEntity.ok(pedidos);
    }
}