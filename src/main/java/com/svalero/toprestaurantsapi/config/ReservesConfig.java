package com.svalero.toprestaurantsapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservesConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
