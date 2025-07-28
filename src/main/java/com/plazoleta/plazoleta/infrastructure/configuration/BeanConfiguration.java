package com.plazoleta.plazoleta.infrastructure.configuration;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.api.ITokenServicePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioValidatorPort;
import com.plazoleta.plazoleta.domain.usecase.PlatoUseCase;
import com.plazoleta.plazoleta.domain.usecase.RestauranteUseCase;
import com.plazoleta.plazoleta.infrastructure.out.feign.adapter.UsuarioValidatorFeignAdapter;
import com.plazoleta.plazoleta.infrastructure.out.feign.client.IUsuarioFeignClient;
import com.plazoleta.plazoleta.infrastructure.out.jpa.adapter.PlatoJpaAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IPlatoRepository;
import com.plazoleta.plazoleta.infrastructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infrastructure.out.jwt.adapter.JwtTokenAdapter;
import com.plazoleta.plazoleta.infrastructure.out.jwt.config.JwtConfig;
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
    public IRestauranteServicePort restauranteServicePort(IRestaurantePersistencePort servicePort, IUsuarioValidatorPort validator) {
        return new RestauranteUseCase(servicePort, validator);
    }
    @Bean
    public IUsuarioValidatorPort usuarioValidatorPort(IUsuarioFeignClient client) {
        return new UsuarioValidatorFeignAdapter(client);
    }
    //Bean para platos
    @Bean
    public IPlatoPersistencePort platoPersistencePort(IPlatoRepository platoRepository, IPlatoEntityMapper platoEntityMapper) {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper);
    }
    @Bean
    public IPlatoServicePort platoServicePort(IPlatoPersistencePort platoPersistencePort,
                                              IRestaurantePersistencePort restaurantePersistencePort,
                                              ITokenServicePort tokenServicePort) {
        return new PlatoUseCase(platoPersistencePort, restaurantePersistencePort, tokenServicePort);
    }
    @Bean
    public ITokenServicePort tokenServicePort(JwtConfig jwtConfig) {
        return new JwtTokenAdapter(jwtConfig);
    }
}
