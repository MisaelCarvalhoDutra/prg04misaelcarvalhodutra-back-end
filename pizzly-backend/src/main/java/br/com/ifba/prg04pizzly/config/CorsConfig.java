package br.com.ifba.prg04pizzly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Configuração global de CORS. Permite que o frontend React acesse a API Spring Boot.
//ou seja,Permite que aplicações frontend autorizadas realizem requisições para esta API


@Configuration //Indica ao Spring que esta classe possui configurações da aplicação
public class CorsConfig {

    // registra uma configuração global de CORS para toda a aplicação
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // cria um configurador das requisições HTTP da aplicação
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) { //método onde definimos quem pode acessar nossa API

                // libera todas as rotas da API para o frontend React
                registry.addMapping("/**")

                        // endereço do frontend durante o desenvolvimento
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://prg04misaelcarvalhodutra-front-end.vercel.app"
                        )
                        // métodos HTTP permitidos
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "PATCH",
                                "DELETE",
                                "OPTIONS"
                        )

                        // permite que qualquer cabeçalho HTTP seja enviado na requisição
                        .allowedHeaders("*");
            }
        };
    }
}