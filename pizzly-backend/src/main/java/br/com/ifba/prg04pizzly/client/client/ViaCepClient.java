package br.com.ifba.prg04pizzly.client.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component // a classe passa a ser gerenciada pelo Spring
public class ViaCepClient {

    private final WebClient webClient;

    public ViaCepClient() {

        // configura o WebClient apontando para a API do ViaCEP , q é a api externa
        this.webClient = WebClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }

    public String buscarCep(String cep) {

        // aqui faz uma requisição GET para o ViaCEP
        return webClient
                .get()
                .uri("/{cep}/json", cep) // monta a URL com o CEP que foi informado
                .retrieve()
                .bodyToMono(String.class) // conversão da resposta para String
                .block(); // espera o retorno da API
    }
}