package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IEmpleadoRestauranteServicePort;
import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.domain.exception.PedidoEnProcesoException;
import com.plazoleta.plazoleta.domain.exception.PlatoNoPerteneceRestauranteException;
import com.plazoleta.plazoleta.domain.model.*;
import com.plazoleta.plazoleta.domain.spi.IPedidoPersistancePort;
import org.aspectj.weaver.patterns.IToken;

import java.math.BigDecimal;
import java.util.List;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.EXISTE_PEDIDO_EN_PROCESO;
import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.PLATO_NO_PERTENECE_RESTAURANTE;

public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistancePort pedidoPersistancePort;
    private final IPlatoServicePort platoServicePort;
    private final ITokenServicePort tokenServicePort;
    private final IEmpleadoRestauranteServicePort empleadoRestauranteServicePort;
    public PedidoUseCase(IPedidoPersistancePort pedidoPersistancePort,
                         IPlatoServicePort platoServicePort,
                         ITokenServicePort tokenServicePort,
                         IEmpleadoRestauranteServicePort empleadoRestauranteServicePort) {
        this.pedidoPersistancePort = pedidoPersistancePort;
        this.platoServicePort = platoServicePort;
        this.tokenServicePort = tokenServicePort;
        this.empleadoRestauranteServicePort = empleadoRestauranteServicePort;
    }

    @Override
    public void crearPedido(Pedido pedido) {

        //verificar que el cliente no tiene pedido en proceso
        Long idUsuario = tokenServicePort.getUserIdFromToken();
        //logica para verificar si el usuario tiene un pedido en proceso
        validarPedidoEnProceso(idUsuario);
        //iterar por platos del pedido y asignar el precio unitario
        for (PedidoPlato e : pedido.getPlatos()) {
            Plato plato = platoServicePort.obtenerPlatoPorId(e.getIdPlato());
            if(!pedido.getIdRestaurante().equals(plato.getIdRestaurante())) {
                throw new PlatoNoPerteneceRestauranteException(PLATO_NO_PERTENECE_RESTAURANTE);
            }
            e.setNombrePlato(plato.getNombre());
            e.setPrecioUnitario(BigDecimal.valueOf(plato.getPrecio()));
        }

        // Guardar el pedido
        pedido.setFechaCreacion(java.time.LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setIdUsuario(idUsuario);
        pedido.calcularPrecioTotal();
        pedidoPersistancePort.crearPedido(pedido);
    }
    @Override
    public List<Pedido> buscarPedidosClienteEnProceso(Long idUsuario) {
        // Implementación para buscar pedidos del cliente
        return pedidoPersistancePort.buscarPedidosClienteEnProceso(idUsuario);
    }

    @Override
    public List<Pedido> buscarPedidosPorEstado(int page, int size, EstadoPedido estado) {
        Long idUsuario = tokenServicePort.getUserIdFromToken();
        //Ver el restauranteDonde trabaja
        Long idRestaurante = empleadoRestauranteServicePort.obtenerRestauranteDeEmpleado(idUsuario);
        //Obtener pedidos
        List<Pedido> pedidos = pedidoPersistancePort.buscarPedidosPorEstado(idUsuario,idRestaurante,estado,page,size);
        return pedidos;
    }

    private void validarPedidoEnProceso(Long idUsuario) {
        List<Pedido> listaPedidos = buscarPedidosClienteEnProceso(idUsuario);
        if (!listaPedidos.isEmpty()) {
            throw new PedidoEnProcesoException(EXISTE_PEDIDO_EN_PROCESO);
        }
    }


}
