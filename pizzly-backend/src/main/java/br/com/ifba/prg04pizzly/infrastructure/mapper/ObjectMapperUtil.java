package br.com.ifba.prg04pizzly.infrastructure.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Classe de configuração responsável por disponibilizar uma instância do ModelMapper para toda a aplicação.
//é utilizado para converter automaticamente entidades em DTOs e DTOs em entidades, reduzindo código repetitivo
@Configuration
public class ObjectMapperUtil {

    //registra um ModelMapper como Bean do Spring. Assim, qualquer classe pode utilizá-lo através de injeção de dependência.
    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }
}