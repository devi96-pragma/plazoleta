package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.*;
import com.plazoleta.plazoleta.domain.exception.*;
import com.plazoleta.plazoleta.domain.model.*;
import com.plazoleta.plazoleta.domain.spi.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.*;

public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistancePort pedidoPersistancePort;
    private final IPlatoServicePort platoServicePort;
    private final ITokenServicePort tokenServicePort;
    private final IEmpleadoRestauranteServicePort empleadoRestauranteServicePort;
    private final IPedidoEventPublishPort pedidoEventPublishPort;
    private final IPinGeneratorPort pinGeneratorPort;
    private final IUsuarioConsultarPort usuarioConsultarPort;
    private final IPedidoCambioEstadoEventPublishPort pedidoCambioEstadoEventPublishPort;

    public PedidoUseCase(IPedidoPersistancePort pedidoPersistancePort,
                         IPlatoServicePort platoServicePort,
                         ITokenServicePort tokenServicePort,
                         IEmpleadoRestauranteServicePort empleadoRestauranteServicePort,
                         IPedidoEventPublishPort pedidoEventPublishPort,
                         IPinGeneratorPort pinGeneratorPort,
                         IUsuarioConsultarPort usuarioConsultarPort,
                         IPedidoCambioEstadoEventPublishPort pedidoCambioEstadoEventPublishPort) {
        this.pedidoPersistancePort = pedidoPersistancePort;
        this.platoServicePort = platoServicePort;
        this.tokenServicePort = tokenServicePort;
        this.empleadoRestauranteServicePort = empleadoRestauranteServicePort;
        this.pedidoEventPublishPort = pedidoEventPublishPort;
        this.pinGeneratorPort = pinGeneratorPort;
        this.usuarioConsultarPort = usuarioConsultarPort;
        this.pedidoCambioEstadoEventPublishPort = pedidoCambioEstadoEventPublishPort;
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
        LocalDateTime fechaCreacion =LocalDateTime.now();
        pedido.setFechaCreacion(fechaCreacion);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setIdUsuario(idUsuario);
        pedido.calcularPrecioTotal();
        Pedido pedidoCreado = pedidoPersistancePort.crearPedido(pedido);
        PedidoEstadoCambiadoEvento evento =
                new PedidoEstadoCambiadoEvento(
                        pedidoCreado.getId(),
                        pedido.getIdUsuario(),
                        null,
                        EstadoPedido.PENDIENTE,
                        fechaCreacion);
        pedidoCambioEstadoEventPublishPort
                .publicarPedidoEventoCambioEstado(evento);
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

    @Override
    public void asignarmePedido(Long idPedido) {
        //obtener el id del usuario
        Long idEmpleado = tokenServicePort.getUserIdFromToken();
        Pedido pedido = buscarPedidoPorId(idPedido);
        //Validar estado
        if(!pedido.getEstado().equals(EstadoPedido.PENDIENTE)){
            throw new PedidosNoEnEstadoPendienteException(PEDIDO_NO_EN_ESTADO_PENDIENTE);
        }
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        pedido.setIdEmpleado(idEmpleado);
        LocalDateTime fechaCambio = LocalDateTime.now();
        pedidoPersistancePort.actualizarPedido(pedido);
        PedidoEstadoCambiadoEvento evento =
                new PedidoEstadoCambiadoEvento(
                        pedido.getId(),
                        pedido.getIdUsuario(),
                        idEmpleado,
                        pedido.getEstado(),
                        fechaCambio
                        );
        pedidoCambioEstadoEventPublishPort
                .publicarPedidoEventoCambioEstado(evento);
    }

    @Override
    public void notificarPedidoCliente(Long idPedido) {
        Long idEmpleado = tokenServicePort.getUserIdFromToken();
        Pedido pedido = buscarPedidoPorId(idPedido);
        if(!pedido.getIdEmpleado().equals(idEmpleado)){
            throw new PedidoNoEstaAsignadoAlEmpleadoException(PEDIDO_NO_ASIGNADO_EMPLEADO);
        }
        //Validar estado
        if(!pedido.getEstado().equals(EstadoPedido.EN_PREPARACION)){
            throw new PedidosNoEnEstadoEnPreparacionException(PEDIDO_NO_EN_ESTADO_EN_PREPARACION);
        }
        //Generar pin y guardar pedido
        String pin = pinGeneratorPort.generarPin();
        pedido.setPin(pin);
        pedido.setEstado(EstadoPedido.LISTO);
        LocalDateTime fechaCambio = LocalDateTime.now();
        pedidoPersistancePort.actualizarPedido(pedido);

        Usuario usuario = usuarioConsultarPort.encontrarCliente(pedido.getIdUsuario());
        PedidoListoEvento evento = new PedidoListoEvento(
                pedido.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCelular(),
                pedido.getPrecioTotal(),
                pin,
                pedido.getId());
        pedidoEventPublishPort.notificarPedidoCliente(evento);

        PedidoEstadoCambiadoEvento eventoEstadoCambiado =
                new PedidoEstadoCambiadoEvento(
                        pedido.getId(),
                        pedido.getIdUsuario(),
                        idEmpleado,
                        pedido.getEstado(),
                        fechaCambio
                );
        pedidoCambioEstadoEventPublishPort
                .publicarPedidoEventoCambioEstado(eventoEstadoCambiado);
    }

    @Override
    public void entregarPedido(Long idPedido, String pin) {
        //Validar que el pedido este en estado Listo
        Long idEmpleado = tokenServicePort.getUserIdFromToken();
        Pedido pedido = buscarPedidoPorId(idPedido);
        if(!pedido.getIdEmpleado().equals(idEmpleado)){
            throw new PedidoNoEstaAsignadoAlEmpleadoException(PEDIDO_NO_ASIGNADO_EMPLEADO);
        }
        if(!pedido.getEstado().equals(EstadoPedido.LISTO)){
            throw new PedidosNoEnEstadoListoException(PEDIDO_NO_EN_ESTADO_LISTO);
        }
        //Validar que el cliente tiene el pin correcto.
        if(!pedido.getPin().equals(pin)){
            throw new PinIncorrectoException(PIN_INCORRECTO);
        }
        //Se le entrega el pedido y se actualiza
        pedido.setEstado(EstadoPedido.ENTREGADO);
        LocalDateTime fechaCambio = LocalDateTime.now();
        pedidoPersistancePort.actualizarPedido(pedido);
        //Guardo el evento para trazabilidad
        PedidoEstadoCambiadoEvento eventoEstadoCambiado =
                new PedidoEstadoCambiadoEvento(
                        pedido.getId(),
                        pedido.getIdUsuario(),
                        idEmpleado,
                        pedido.getEstado(),
                        fechaCambio
                );
        pedidoCambioEstadoEventPublishPort
                .publicarPedidoEventoCambioEstado(eventoEstadoCambiado);
    }

    @Override
    public void cancelarPedido(Long idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        if(!pedido.getEstado().equals(EstadoPedido.PENDIENTE)){
            throw new PedidoNoCancelableException(PEDIDO_NO_CANCELABLE);
        }
        pedido.setEstado(EstadoPedido.CANCELADO);
        LocalDateTime fechaCambio = LocalDateTime.now();
        pedidoPersistancePort.actualizarPedido(pedido);
        //Guardo el evento para trazabilidad
        PedidoEstadoCambiadoEvento eventoEstadoCambiado =
                new PedidoEstadoCambiadoEvento(
                        pedido.getId(),
                        pedido.getIdUsuario(),
                        null,
                        pedido.getEstado(),
                        fechaCambio
                );
        pedidoCambioEstadoEventPublishPort
                .publicarPedidoEventoCambioEstado(eventoEstadoCambiado);
    }

    private Pedido buscarPedidoPorId(Long idPedido){
        return pedidoPersistancePort.buscarPedidoPorId(idPedido)
                .orElseThrow(() -> new PedidoNoEncontradoException(PEDIDO_NO_EXISTE));
    }
    private void validarPedidoEnProceso(Long idUsuario) {
        List<Pedido> listaPedidos = buscarPedidosClienteEnProceso(idUsuario);
        if (!listaPedidos.isEmpty()) {
            throw new PedidoEnProcesoException(EXISTE_PEDIDO_EN_PROCESO);
        }
    }
}
