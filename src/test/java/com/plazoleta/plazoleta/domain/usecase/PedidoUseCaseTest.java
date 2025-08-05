package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IEmpleadoRestauranteServicePort;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.domain.exception.*;
import com.plazoleta.plazoleta.domain.model.*;
import com.plazoleta.plazoleta.domain.spi.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.PLATO_NO_PERTENECE_RESTAURANTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {

    @Mock
    private IPedidoPersistancePort pedidoPersistancePort;
    @Mock
    private IPinGeneratorPort pinGeneratorPort;
    @Mock
    private IUsuarioConsultarPort usuarioConsultarPort;

    @Mock
    private IPlatoServicePort platoServicePort;

    @Mock
    private ITokenServicePort tokenServicePort;

    @Mock
    private IEmpleadoRestauranteServicePort empleadoRestauranteServicePort;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    @Mock
    private IPedidoEventPublishPort pedidoEventPublishPort;
    @Mock
    private IPedidoCambioEstadoEventPublishPort pedidoCambioEstadoEventPublishPort;

    @Test
    void testCrearPedido_conDatosValidos_creaPedidoCorrectamente() {
        // Arrange
        Long idUsuario = 10L;
        Long idRestaurante = 20L;
        Long idPlato = 100L;

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setIdPlato(idPlato);
        pedidoPlato.setCantidad(2);
        List<PedidoPlato> platos = List.of(pedidoPlato);

        Pedido pedido = new Pedido();
        pedido.setIdRestaurante(idRestaurante);
        pedido.setPlatos(platos);

        Plato plato = new Plato(
                idPlato,
                "Tallarines",
                18.5f,
                "Tallarines verdes",
                "http://img.com/tallarines.jpg",
                Categoria.PLATO_PRINCIPAL,
                true,
                idRestaurante
        );

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idUsuario);
        when(pedidoPersistancePort.buscarPedidosClienteEnProceso(idUsuario)).thenReturn(List.of());
        when(platoServicePort.obtenerPlatoPorId(idPlato)).thenReturn(plato);
        when(pedidoPersistancePort.crearPedido(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(1L); // simular que se le asigna ID
            return p;
        });

        // Act
        pedidoUseCase.crearPedido(pedido);

        // Assert
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(idUsuario, pedido.getIdUsuario());
        assertNotNull(pedido.getFechaCreacion());
        assertEquals(new BigDecimal("18.5"), pedido.getPlatos().get(0).getPrecioUnitario());
        assertEquals("Tallarines", pedido.getPlatos().get(0).getNombrePlato());
        // Verificación de persistencia
        verify(pedidoPersistancePort).crearPedido(any(Pedido.class));
        verify(pedidoCambioEstadoEventPublishPort).publicarPedidoEventoCambioEstado(any());
    }
    @Test
    void testCrearPedido_conPlatoDeOtroRestaurante_lanzaExcepcion() {
        // Arrange
        Long idUsuario = 10L;
        Long idRestaurantePedido = 1L;
        Long idRestaurantePlato = 99L;
        Long idPlato = 123L;

        PedidoPlato pedidoPlato = new PedidoPlato();
        pedidoPlato.setIdPlato(idPlato);

        Pedido pedido = new Pedido();
        pedido.setIdRestaurante(idRestaurantePedido);
        pedido.setPlatos(List.of(pedidoPlato));

        Plato plato = new Plato(
                idPlato,
                "Ceviche",
                20.0f,
                "Ceviche clásico",
                "http://img.com/ceviche.jpg",
                Categoria.ENTRADA,
                true,
                idRestaurantePlato // distinto restaurante
        );

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idUsuario);
        when(pedidoPersistancePort.buscarPedidosClienteEnProceso(idUsuario)).thenReturn(List.of());
        when(platoServicePort.obtenerPlatoPorId(idPlato)).thenReturn(plato);

        // Act & Assert
        PlatoNoPerteneceRestauranteException ex = assertThrows(
                PlatoNoPerteneceRestauranteException.class,
                () -> pedidoUseCase.crearPedido(pedido)
        );
        assertEquals(PLATO_NO_PERTENECE_RESTAURANTE, ex.getMessage());
        verify(pedidoPersistancePort, never()).crearPedido(any());
    }

    @Test
    void testBuscarPedidosClienteEnProceso_delegaCorrectamente() {
        // Arrange
        Long idUsuario = 5L;
        List<Pedido> pedidosMock = List.of(new Pedido());
        when(pedidoPersistancePort.buscarPedidosClienteEnProceso(idUsuario)).thenReturn(pedidosMock);

        // Act
        List<Pedido> resultado = pedidoUseCase.buscarPedidosClienteEnProceso(idUsuario);

        // Assert
        assertEquals(pedidosMock, resultado);
        verify(pedidoPersistancePort).buscarPedidosClienteEnProceso(idUsuario);
    }
    @Test
    void testBuscarPedidosPorEstado_retornaPedidosCorrectamente() {
        // Arrange
        Long idUsuario = 10L;
        Long idRestaurante = 99L;
        int page = 1;
        int size = 5;
        EstadoPedido estado = EstadoPedido.PENDIENTE;

        List<Pedido> pedidosMock = List.of(new Pedido());

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idUsuario);
        when(empleadoRestauranteServicePort.obtenerRestauranteDeEmpleado(idUsuario)).thenReturn(idRestaurante);
        when(pedidoPersistancePort.buscarPedidosPorEstado(idUsuario, idRestaurante, estado, page, size)).thenReturn(pedidosMock);

        // Act
        List<Pedido> resultado = pedidoUseCase.buscarPedidosPorEstado(page, size, estado);

        // Assert
        assertEquals(pedidosMock, resultado);
        verify(tokenServicePort).getUserIdFromToken();
        verify(empleadoRestauranteServicePort).obtenerRestauranteDeEmpleado(idUsuario);
        verify(pedidoPersistancePort).buscarPedidosPorEstado(idUsuario, idRestaurante, estado, page, size);
    }
    //Happy path actualizar pedido y asignarmelo a mi
    @Test
    void testAsignarmePedido_actualizarPedido(){
        //arrange
        Long idEmpleado = 1L;
        Long idPedido = 1L;
        Pedido pedidoEsperado = new Pedido();
        pedidoEsperado.setEstado(EstadoPedido.PENDIENTE);

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idEmpleado);
        when(pedidoPersistancePort.buscarPedidoPorId(idPedido)).thenReturn(Optional.of(pedidoEsperado));
        //act
        pedidoUseCase.asignarmePedido(idPedido);
        //assert
        assertEquals(EstadoPedido.EN_PREPARACION, pedidoEsperado.getEstado());
        assertEquals(idEmpleado, pedidoEsperado.getIdEmpleado());

        // verify que persistió y publicó evento
        verify(pedidoCambioEstadoEventPublishPort,times(1)).publicarPedidoEventoCambioEstado(any());
        //verify(pedidoPersistancePort).buscarPedidoPorId(idPedido);
        verify(pedidoPersistancePort,times(1)).actualizarPedido(any());
    }
    //Happy path notificar
    @Test
    void testNotificarPedidoCliente_happyPath() {
        Long idEmpleado = 1L, idPedido = 2L, idUsuario = 5L;
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdUsuario(idUsuario);
        pedido.setIdEmpleado(idEmpleado);
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        pedido.setPrecioTotal(new BigDecimal("50.00"));

        Usuario usuario = new Usuario();
        usuario.setNombre("Ana"); usuario.setApellido("Pérez"); usuario.setCelular("999");

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idEmpleado);
        when(pedidoPersistancePort.buscarPedidoPorId(idPedido))
                .thenReturn(Optional.of(pedido));
        when(pinGeneratorPort.generarPin()).thenReturn("1234");
        when(usuarioConsultarPort.encontrarCliente(idUsuario)).thenReturn(usuario);

        pedidoUseCase.notificarPedidoCliente(idPedido);

        assertEquals(EstadoPedido.LISTO, pedido.getEstado());
        assertEquals("1234", pedido.getPin());
        verify(pedidoPersistancePort).actualizarPedido(pedido);
        verify(pedidoEventPublishPort).notificarPedidoCliente(any());
        verify(pedidoCambioEstadoEventPublishPort).publicarPedidoEventoCambioEstado(any());
    }

    @Test
    void testNotificarPedidoCliente_malEmpleado() {
        Long wrongId = 0L;
        Pedido pedido = new Pedido();
        pedido.setIdEmpleado(1L);
        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        when(tokenServicePort.getUserIdFromToken()).thenReturn(wrongId);
        when(pedidoPersistancePort.buscarPedidoPorId(1L))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidoNoEstaAsignadoAlEmpleadoException.class,
                () -> pedidoUseCase.notificarPedidoCliente(1L));
    }

    @Test
    void testNotificarPedidoCliente_malEstado() {
        Long idEmpleado = 1L, idPedido = 2L;
        Pedido pedido = new Pedido();
        pedido.setIdEmpleado(idEmpleado);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        when(tokenServicePort.getUserIdFromToken()).thenReturn(idEmpleado);
        when(pedidoPersistancePort.buscarPedidoPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidosNoEnEstadoEnPreparacionException.class,
                () -> pedidoUseCase.notificarPedidoCliente(idPedido));
    }

    @Test
    void testEntregarPedido_happyPath() {
        Long idEmpleado = 1L, idPedido = 3L;
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdUsuario(5L);
        pedido.setIdEmpleado(idEmpleado);
        pedido.setEstado(EstadoPedido.LISTO);
        pedido.setPin("0000");

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idEmpleado);
        when(pedidoPersistancePort.buscarPedidoPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        pedidoUseCase.entregarPedido(idPedido, "0000");

        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
        verify(pedidoPersistancePort).actualizarPedido(pedido);
        verify(pedidoCambioEstadoEventPublishPort).publicarPedidoEventoCambioEstado(any());
    }

    @Test
    void testEntregarPedido_pinIncorrecto() {
        Long idEmpleado = 1L, idPedido = 3L;
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setIdEmpleado(idEmpleado);
        pedido.setEstado(EstadoPedido.LISTO);
        pedido.setPin("1111");

        when(tokenServicePort.getUserIdFromToken()).thenReturn(idEmpleado);
        when(pedidoPersistancePort.buscarPedidoPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        assertThrows(PinIncorrectoException.class,
                () -> pedidoUseCase.entregarPedido(idPedido, "0000"));
    }

    @Test
    void testCancelarPedido_happyPath() {
        Long idPedido = 4L;
        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PENDIENTE);

        when(pedidoPersistancePort.buscarPedidoPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        pedidoUseCase.cancelarPedido(idPedido);

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        verify(pedidoPersistancePort).actualizarPedido(pedido);
        verify(pedidoCambioEstadoEventPublishPort).publicarPedidoEventoCambioEstado(any());
    }

    @Test
    void testCancelarPedido_noPendiente() {
        Long idPedido = 4L;
        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.EN_PREPARACION);

        when(pedidoPersistancePort.buscarPedidoPorId(idPedido))
                .thenReturn(Optional.of(pedido));

        assertThrows(PedidoNoCancelableException.class,
                () -> pedidoUseCase.cancelarPedido(idPedido));
    }
}