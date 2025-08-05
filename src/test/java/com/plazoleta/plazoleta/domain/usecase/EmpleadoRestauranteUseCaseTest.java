package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.EmpleadoSinRestauranteAsignadoException;
import com.plazoleta.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.EMPLEADO_SIN_RESTAURANTE_ASIGNADO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoRestauranteUseCaseTest {
    @Mock
    private IEmpleadoRestaurantePersistencePort persistencePort;

    @InjectMocks
    private EmpleadoRestauranteUseCase useCase;

    @Test
    void obtenerRestauranteDeEmpleado_existente_retornaId() {
        // Arrange
        Long idUsuario = 1L;
        Long idRestaurante = 10L;
        when(persistencePort.obtenerIdRestaurantePorUsuario(idUsuario))
                .thenReturn(Optional.of(idRestaurante));

        // Act
        Long result = useCase.obtenerRestauranteDeEmpleado(idUsuario);

        // Assert
        assertThat(result).isEqualTo(idRestaurante);
    }
    @Test
    void obtenerRestauranteDeEmpleado_error_noExiste(){
        // Arrange
        Long idUsuario = 1L;
        when(persistencePort.obtenerIdRestaurantePorUsuario(idUsuario))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.obtenerRestauranteDeEmpleado(idUsuario))
                .isInstanceOf(EmpleadoSinRestauranteAsignadoException.class)
                .hasMessage(EMPLEADO_SIN_RESTAURANTE_ASIGNADO);
    }
}
