package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.domain.exception.PlatoNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEsDelUsuarioException;
import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlatoUseCaseTest {
    @Mock
    private IPlatoPersistencePort platoPersistencePort;
    @Mock
    private IRestauranteServicePort restauranteServicePort;
    @InjectMocks
    private PlatoUseCase platoUseCase;
    @Mock
    private ITokenServicePort tokenServicePort;

    //Happy Path: crear un plato cuando el usuario es propietario del restaurante y el restaurante existe
    @Test
    void crearPlato_conDatosValidos_exito() {
        // Arrange
        Plato plato = new Plato();
        plato.setIdRestaurante(1L);
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setUsuarioId(2L); // el restaurante es del usuario 2L

        when(restauranteServicePort.findRestauranteById(1L))
                .thenReturn(restaurante);
        when(tokenServicePort.getUserIdFromToken())
                .thenReturn(2L); // Simulamos que el usuario autenticado es el propietario del restaurante
        // Act
        platoUseCase.crearPlato(plato);

        // Assert
        verify(platoPersistencePort).crearPlato(plato);
    }
    //No happy path: crear un plato cuando el usuario no es propietario del restaurante
    @Test
    void crearPlato_conUsuarioNoPropietario_deberiaLanzarExcepcion() {
        // Arrange
        Plato plato = new Plato();
        plato.setIdRestaurante(1L);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setUsuarioId(2L); // el restaurante es del usuario 2L

        when(restauranteServicePort.findRestauranteById(1L))
                .thenReturn(restaurante);
        when(tokenServicePort.getUserIdFromToken())
                .thenReturn(3L);

        // act & assert
        assertThrows(
            RestauranteNoEsDelUsuarioException.class,
            () -> platoUseCase.crearPlato(plato)
        );
        verify(restauranteServicePort,times(1))
                .findRestauranteById(1L); // Verifica que se intentó buscar el restaurante
        verify(platoPersistencePort, never()).crearPlato(plato); // Verifica que no se creó el plato
    }
    //No happy path: crear un plato cuando el restaurante no existe
    @Test
    void crearPlato_conRestauranteNoExistente_deberiaLanzarExcepcion() {
        // Arrange
        Plato plato = new Plato();
        plato.setIdRestaurante(1L);
        // No se asigna un restaurante, simulando que no existe
        when(restauranteServicePort.findRestauranteById(1L))
                .thenThrow(new RestauranteNoEncontradoException("Restaurante no encontrado"));
        // act & assert
        assertThrows(
                RestauranteNoEncontradoException.class,
                () -> platoUseCase.crearPlato(plato)
        );
        verify(restauranteServicePort,times(1))
                .findRestauranteById(1L); // Verifica que se intentó buscar el restaurante
        verify(platoPersistencePort, never()).crearPlato(plato); // Verifica que no se creó el plato
    }
    //Happy Path: actualizar un plato cuando el plato existe
    @Test
    void actualizarPlato_conPlatoExistente_exito() {
        // Arrange
        Plato plato = new Plato();
        plato.setPrecio(10);
        plato.setDescripcion("Nueva Descripción");

        Plato platoExistente = new Plato();
        platoExistente.setId(5L);
        platoExistente.setDescripcion("Plato Original");
        platoExistente.setPrecio(11);
        platoExistente.setIdRestaurante(1L); // ID del restaurante al que pertenece el plato
        //Restaurante del plato existente
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setUsuarioId(2L);

        when(platoPersistencePort.findPlatoById(5L))
                .thenReturn(Optional.of(platoExistente));
        //Validar restaurante del plato
        when(restauranteServicePort.findRestauranteById(1L)).thenReturn(restaurante);
        when(tokenServicePort.getUserIdFromToken())
                .thenReturn(2L); // Simulamos que el usuario autenticado es el propietario del restaurante
        //Actualizar el plato existente
        when(platoPersistencePort.actualizarPlato(platoExistente)).thenReturn(platoExistente);

        // Act
        Plato resultado = platoUseCase.actualizarPlato(plato, 5L);

        // Assert
        assertThat(resultado.getDescripcion()).isEqualTo("Nueva Descripción");
        assertThat(resultado.getPrecio()).isEqualTo(10);
        verify(platoPersistencePort, times(1)).findPlatoById(5L);
        verify(platoPersistencePort, times(1)).actualizarPlato(platoExistente);
    }
    //No happy path: actualizar un plato cuando el plato no existe
    @Test
    void actualizarPlato_conPlatoNoExistente_deberiaLanzarExcepcion() {
        // Arrange
        Plato plato = new Plato();

        when(platoPersistencePort.findPlatoById(5L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PlatoNoEncontradoException.class,
            () -> platoUseCase.actualizarPlato(plato, 5L)
        );
        verify(platoPersistencePort, times(1)).findPlatoById(5L);
        verify(platoPersistencePort, never()).actualizarPlato(any());
    }
    //Happy Path: obtener un plato por ID cuando el plato existe
    @Test
    void obtenerPlatoPorId_conPlatoExistente_exito() {
        // Arrange
        Plato plato = new Plato();
        plato.setId(5L);
        plato.setDescripcion("Plato de Prueba");

        when(platoPersistencePort.findPlatoById(5L))
                .thenReturn(Optional.of(plato));

        // Act
        Plato resultado = platoUseCase.obtenerPlatoPorId(5L);

        // Assert
        assertThat(resultado.getId()).isEqualTo(5L);
        assertThat(resultado.getDescripcion()).isEqualTo("Plato de Prueba");
        verify(platoPersistencePort, times(1)).findPlatoById(5L);
    }
    //No happy path: obtener un plato por ID cuando el plato no existe
    @Test
    void obtenerPlatoPorId_conPlatoNoExistente_deberiaLanzarExcepcion() {
        // Arrange
        when(platoPersistencePort.findPlatoById(5L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PlatoNoEncontradoException.class,
            () -> platoUseCase.obtenerPlatoPorId(5L)
        );
        verify(platoPersistencePort, times(1)).findPlatoById(5L);
    }
    //Happy Path: habilitar un plato cuando el plato existe y se actualiza correctamente
    @Test
    void habilitarPlato_conPlatoExistente_exito() {
        //Arrange
        Plato plato = new Plato();
        plato.setId(5L);
        plato.setActivo(false);
        plato.setIdRestaurante(1L);

        Restaurante restaurante = new Restaurante();
        restaurante.setUsuarioId(2L);
        when(platoPersistencePort.findPlatoById(5L)).thenReturn(Optional.of(plato));
        when(restauranteServicePort.findRestauranteById(1L)).thenReturn(restaurante);
        when(tokenServicePort.getUserIdFromToken()).thenReturn(2L); // Simulamos que el usuario autenticado es el propietario del restaurante
        when(platoPersistencePort.actualizarPlato(plato)).thenReturn(plato);
        //Act
        Plato platoResultado = platoUseCase.habilitarDeshabilitarPlato(5L, true);
        //Assert
        assertThat(platoResultado.isActivo()).isTrue();
        assertThat(platoResultado.getId()).isEqualTo(5L);
        assertNotNull(platoResultado, "El resultado no es nulo");
        verify(platoPersistencePort, times(1)).findPlatoById(5L);
        verify(restauranteServicePort, times(1)).findRestauranteById(1L);
        verify(tokenServicePort, times(1)).getUserIdFromToken();
        verify(platoPersistencePort, times(1)).actualizarPlato(plato);
    }
    //No happy path: habilitar un plato cuando el plato no existe
    @Test
    void habilitarPlato_conPlatoNoExistente_deberiaLanzarExcepcion() {
        Plato plato = new Plato();
        //Arrange
        when(platoPersistencePort.findPlatoById(5L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PlatoNoEncontradoException.class,
                () -> platoUseCase.habilitarDeshabilitarPlato( 5L, true)
        );
        verify(platoPersistencePort, times(1)).findPlatoById(5L);
        verify(platoPersistencePort, never()).actualizarPlato(any());
    }
    @Test
    void listarPlatosPorRestaurante_sinCategoria_retornaTodosLosPlatos() {
        // Arrange
        Long restauranteId = 1L;
        int page = 0;
        int size = 5;
        String categoria = null;
        Restaurante restauranteMock = new Restaurante();
        restauranteMock.setUsuarioId(1L);
        List<Plato> platosMock = List.of(
                new Plato(1L, "Arroz Chaufa", 15.5f, "Arroz con pollo y verduras", "http://imagen.com/arroz.jpg",
                        Categoria.PLATO_PRINCIPAL, true, restauranteId)
        );
        when(restauranteServicePort.findRestauranteById(restauranteId)).thenReturn(restauranteMock);
        when(tokenServicePort.getUserIdFromToken()).thenReturn(1L);
        when(platoPersistencePort.listarPlatosPorRestaurante(restauranteId, page, size)).thenReturn(platosMock);

        // Act
        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(restauranteId, page, size, categoria);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Arroz Chaufa", resultado.get(0).getNombre());
        verify(platoPersistencePort).listarPlatosPorRestaurante(restauranteId, page, size);
    }
    @Test
    void listarPlatosPorRestaurante_conCategoria_retornaTodosLosPlatos() {
        // Arrange
        Long restauranteId = 1L;
        int page = 0;
        int size = 5;
        String categoria = "PLATO_PRINCIPAL";
        Restaurante restauranteMock = new Restaurante();
        restauranteMock.setUsuarioId(1L);
        List<Plato> platosMock = List.of(
                new Plato(1L, "Arroz Chaufa", 15.5f, "Arroz con pollo y verduras", "http://imagen.com/arroz.jpg",
                        Categoria.PLATO_PRINCIPAL, true, restauranteId)
        );
        when(restauranteServicePort.findRestauranteById(restauranteId)).thenReturn(restauranteMock);
        when(tokenServicePort.getUserIdFromToken()).thenReturn(1L);
        when(platoPersistencePort.listarplatosPorRestauranteYCategoria(restauranteId,Categoria.PLATO_PRINCIPAL, page, size)).thenReturn(platosMock);

        // Act
        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(restauranteId, page, size, categoria);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Arroz Chaufa", resultado.get(0).getNombre());
        verify(platoPersistencePort).listarplatosPorRestauranteYCategoria(restauranteId, Categoria.PLATO_PRINCIPAL, page, size);
    }
}
