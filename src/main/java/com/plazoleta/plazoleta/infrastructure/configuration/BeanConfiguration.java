package com.plazoleta.plazoleta.infrastructure.configuration;

import com.plazoleta.plazoleta.domain.api.*;
import com.plazoleta.plazoleta.domain.spi.*;
import com.plazoleta.plazoleta.domain.usecase.*;
import com.plazoleta.plazoleta.infrastructure.out.SecureRandom.adapter.PinGenerator;
import com.plazoleta.plazoleta.infrastructure.out.feign.adapter.UsuarioConsultarAdapter;
import com.plazoleta.plazoleta.infrastructure.out.feign.adapter.UsuarioValidatorFeignAdapter;
import com.plazoleta.plazoleta.infrastructure.out.feign.client.IUsuarioFeignClient;
import com.plazoleta.plazoleta.infrastructure.out.jpa.adapter.EmpleadoRestauranteJpaAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jpa.adapter.PedidoJpaAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jpa.adapter.PlatoJpaAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IPedidoEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IPedidoPlatoEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IEmpleadoRestauranteRepository;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IPedidoRepository;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IPlatoRepository;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infrastructure.out.rabbitMQ.adapter.PedidoCambioEstadoRabbitEventPublisher;
import com.plazoleta.plazoleta.infrastructure.out.rabbitMQ.adapter.PedidoRabbitEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class BeanConfiguration {

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort(IRestauranteRepository restauranteRepository,
                                                                  IRestauranteEntityMapper restauranteEntityMapper) {
        return new RestauranteJpaAdapter(restauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IUsuarioValidatorPort usuarioValidatorPort(IUsuarioFeignClient client) {
        return new UsuarioValidatorFeignAdapter(client);
    }
    @Bean
    public IUsuarioConsultarPort usuarioConsultarPort(IUsuarioFeignClient client){
        return new UsuarioConsultarAdapter(client);
    }
    @Bean
    public IPlatoPersistencePort platoPersistencePort(IPlatoRepository platoRepository, IPlatoEntityMapper platoEntityMapper) {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }

    @Bean
    public IPedidoPersistancePort pedidoPersistancePort( IPedidoRepository pedidoRepository,
                                                         IPedidoEntityMapper pedidoEntityMapper,
                                                         IPedidoPlatoEntityMapper pedidoPlatoEntityMapper){
        return new PedidoJpaAdapter(pedidoRepository, pedidoEntityMapper, pedidoPlatoEntityMapper);
    }
    @Bean
    public IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort( IEmpleadoRestauranteRepository empleadoRestauranteRepository){
        return new EmpleadoRestauranteJpaAdapter(empleadoRestauranteRepository);
    }
    //API SERVICES
    @Bean
    public IPlatoServicePort platoServicePort(IPlatoPersistencePort platoPersistencePort,
                                              ITokenServicePort tokenServicePort,
                                              IUsuarioValidatorPort usuarioValidatorPort,
                                              IRestauranteServicePort restauranteServicePort) {
        return new PlatoUseCase(platoPersistencePort, tokenServicePort, usuarioValidatorPort, restauranteServicePort);
    }
    @Bean
    public IRestauranteServicePort restauranteServicePort(IRestaurantePersistencePort servicePort, IUsuarioValidatorPort validator) {
        return new RestauranteUseCase(servicePort, validator);
    }
    //Bean
    @Bean
    public IPedidoServicePort pedidoServicePort(IPedidoPersistancePort pedidoPersistance,
                                                IPlatoServicePort platoService,
                                                ITokenServicePort tokenService,
                                                IEmpleadoRestauranteServicePort empleadoService,
                                                IPedidoEventPublishPort pedidoEventHandlerServicePort,
                                                IPinGeneratorPort pinGeneratorPort,
                                                IUsuarioConsultarPort usuarioConsultarPort,
                                                IPedidoCambioEstadoEventPublishPort pedidoCambioEstadoEventPublishPort){
        return new PedidoUseCase(pedidoPersistance, platoService, tokenService, empleadoService, pedidoEventHandlerServicePort, pinGeneratorPort, usuarioConsultarPort, pedidoCambioEstadoEventPublishPort);
    }
    @Bean
    public IEmpleadoRestauranteServicePort empleadoRestauranteServicePort(IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort){
        return new EmpleadoRestauranteUseCase(empleadoRestaurantePersistencePort);
    }
    @Bean
    public IEmpleadoEventHandlerServicePort empleadoEventHandlerServicePort(IEmpleadoRestaurantePersistencePort persistence){
        return new EmpleadoCreadoEventUseCase(persistence);
    }
    @Bean
    public IPinGeneratorPort pinGeneratorPort(){
        return new PinGenerator(new SecureRandom());
    }
    @Bean
    public IPedidoEventPublishPort pedidoEventPublishPort(RabbitTemplate rabbitTemplate){
        return new PedidoRabbitEventPublisher(rabbitTemplate);
    }
    @Bean
    public IPedidoCambioEstadoEventPublishPort pedidoCambioEstadoEventPublishPort(RabbitTemplate rabbitTemplate){
        return new PedidoCambioEstadoRabbitEventPublisher(rabbitTemplate);
    }

}
