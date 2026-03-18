package com.krishna.banking.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper getModelMapper(){
//        ModelMapper modelMapper= new ModelMapper();
//        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return new ModelMapper();
    }
}
