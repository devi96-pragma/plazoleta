package com.plazoleta.plazoleta.domain.constantes;

public final class Constantes {
    private Constantes() {
    }
    public static final class MensajesError{
        public static final String RESTAURANTE_NO_PERTENECE_AL_USUARIO = "El usuario no es propietario del restaurante";
        public static final String PLATO_NO_ENCONTRADO = "Plato no encontrado";
        public static final String PLATO_YA_EN_ESTADO_SOLICITADO = "El plato ya se encuentra en estado solicitado";
        public static final String USUARIO_NO_ES_PROPIETARIO = "El usuario no es propietario o no existe";
        public static final String RESTAURANTE_NO_ENCONTRADO = "Restaurante no encontrado";
        public static final String CATEGORIA_NO_ENCONTRADA = "La categoria no existe";
        public static final String EXISTE_PEDIDO_EN_PROCESO = "El usuario ya tiene un pedido en proceso";
        public static final String PLATO_NO_PERTENECE_RESTAURANTE = "El plato no pertenece al restaurante";
        public static final String EMPLEADO_SIN_RESTAURANTE_ASIGNADO = "El empleado no tiene un restaurante asignado";
        public static final String PEDIDO_NO_EXISTE = "No existe un pedido con ese ID";
        public static final String PEDIDO_NO_EN_ESTADO_PENDIENTE = "El pedido debe estar en estado pendiente para seguir el flujo";
        public static final String PEDIDO_NO_ASIGNADO_EMPLEADO = "El pedido no esta asignado al empleado";
        public static final String PEDIDO_NO_EN_ESTADO_EN_PREPARACION = "El pedido debe estar en estado En preparacion para seguir el flujo";
        public static final String PEDIDO_NO_EN_ESTADO_LISTO = "El pedido debe estar en estado Listo para seguir el flujo";
        public static final String PIN_INCORRECTO= "El pin ingresado es incorrecto";
        public static final String PEDIDO_NO_CANCELABLE = "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse";
    }
}
