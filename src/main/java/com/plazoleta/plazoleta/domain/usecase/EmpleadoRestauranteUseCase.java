package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IEmpleadoRestauranteServicePort;
import com.plazoleta.plazoleta.domain.exception.EmpleadoSinRestauranteAsignadoException;
import com.plazoleta.plazoleta.domain.spi.IEmpleadoRestaurantePersistencePort;

import static com.plazoleta.plazoleta.domain.constantes.Constantes.MensajesError.EMPLEADO_SIN_RESTAURANTE_ASIGNADO;

public class EmpleadoRestauranteUseCase implements IEmpleadoRestauranteServicePort {

    private final IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort;

    public EmpleadoRestauranteUseCase(IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort) {
        this.empleadoRestaurantePersistencePort = empleadoRestaurantePersistencePort;
    }

    @Override
    public Long obtenerRestauranteDeEmpleado(Long idUsuario) {
        return empleadoRestaurantePersistencePort.obtenerIdRestaurantePorUsuario(idUsuario)
                .orElseThrow(() -> new EmpleadoSinRestauranteAsignadoException(EMPLEADO_SIN_RESTAURANTE_ASIGNADO));
    }
}
