package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.PlatoNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEsDelUsuarioException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlatoUseCaseTest {
    @Mock
    private IPlatoPersistencePort platoPersistencePort;
    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;
    @InjectMocks
    private PlatoUseCase platoUseCase;

    //Happy Path: crear un plato cuando el usuario es propietario del restaurante y el restaurante existe
    @Test
    void crearPlato_conDatosValidos_exito() {
        // Arrange
        Plato plato = new Plato();
        plato.setIdRestaurante(1L);
        plato.setIdUsuario(2L); // tiene asignado el usuario 2L
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setUsuarioId(2L); // el restaurante es del usuario 2L

        when(restaurantePersistencePort.findRestauranteById(1L))
                .thenReturn(Optional.of(restaurante));

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
        plato.setIdUsuario(3L); // tiene asignado el usuario 3L
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setUsuarioId(2L); // el restaurante es del usuario 2L
        when(restaurantePersistencePort.findRestauranteById(1L))
                .thenReturn(Optional.of(restaurante));
        // act & assert
        assertThrows(
            RestauranteNoEsDelUsuarioException.class,
            () -> platoUseCase.crearPlato(plato)
        );
        verify(restaurantePersistencePort,times(1))
                .findRestauranteById(1L); // Verifica que se intentó buscar el restaurante
        verify(platoPersistencePort, never()).crearPlato(plato); // Verifica que no se creó el plato
    }
    //No happy path: crear un plato cuando el restaurante no existe
    @Test
    void crearPlato_conRestauranteNoExistente_deberiaLanzarExcepcion() {
        // Arrange
        Plato plato = new Plato();
        plato.setIdRestaurante(1L);
        plato.setIdUsuario(3L); // tiene asignado el usuario 3L
        // No se asigna un restaurante, simulando que no existe
        when(restaurantePersistencePort.findRestauranteById(1L))
                .thenReturn(Optional.empty());
        // act & assert
        assertThrows(
                RestauranteNoEncontradoException.class,
                () -> platoUseCase.crearPlato(plato)
        );
        verify(restaurantePersistencePort,times(1))
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

        when(platoPersistencePort.findPlatoById(5L))
                .thenReturn(Optional.of(platoExistente));
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
}
