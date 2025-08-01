package com.plazoleta.plazoleta.infrastructure.configuration;

import com.plazoleta.plazoleta.domain.api.*;
import com.plazoleta.plazoleta.domain.spi.*;
import com.plazoleta.plazoleta.domain.usecase.*;
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
import com.plazoleta.plazoleta.infrastructure.out.jwt.adapter.JwtTokenAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jwt.config.JwtConfig;
import com.plazoleta.plazoleta.infrastructure.out.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
   /* @Bean
    public ITokenServicePort tokenServicePort(JwtConfig jwtConfig) {
        return new JwtTokenAdapter(jwtConfig);
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenAdapter adapter){
        return new JwtAuthenticationFilter(adapter);
    }*/
    /*
    @Bean
    public ITokenServicePort tokenServicePort(JwtConfig jwtConfig) {
       return new JwtTokenAdapter(jwtConfig);
    }*/
    @Bean
    public IRestauranteServicePort restauranteServicePort(IRestaurantePersistencePort servicePort, IUsuarioValidatorPort validator) {
        return new RestauranteUseCase(servicePort, validator);
    }
    //Bean
    @Bean
    public IPedidoServicePort pedidoServicePort(IPedidoPersistancePort pedidoPersistance,
                                                IPlatoServicePort platoService,
                                                ITokenServicePort tokenService,
                                                IEmpleadoRestauranteServicePort empleadoService){
        return new PedidoUseCase(pedidoPersistance, platoService, tokenService, empleadoService);
    }
    @Bean
    public IEmpleadoRestauranteServicePort empleadoRestauranteServicePort(IEmpleadoRestaurantePersistencePort empleadoRestaurantePersistencePort){
        return new EmpleadoRestauranteUseCase(empleadoRestaurantePersistencePort);
    }
    @Bean
    public IEmpleadoEventHandlerServicePort empleadoEventHandlerServicePort(IEmpleadoRestaurantePersistencePort persistence){
        return new EmpleadoCreadoEventUseCase(persistence);
    }

}
