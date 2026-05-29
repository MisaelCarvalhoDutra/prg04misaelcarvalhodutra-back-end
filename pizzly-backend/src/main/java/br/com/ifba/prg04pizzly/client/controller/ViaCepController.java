package br.com.ifba.prg04pizzly.client.controller;

import br.com.ifba.prg04pizzly.client.client.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep") // define a rota principal da API
@CrossOrigin("*") // aqui permite o  acesso de qualquer origem (frontend)
@RequiredArgsConstructor // cria automaticamente o construtor para atributos final
public class ViaCepController {

    // Injeta automaticamente o ViaCepClient
    private final ViaCepClient viaCepClient;

    // O endpoint GET para buscar um CEP
    @GetMapping("/{cep}")
    public String buscarCep(

            // pega o CEP enviado pela URL
            @PathVariable String cep
    ) {

        // Chama o client para consultar a API externa ViaCEP
        // e retorna os dados encontrados
        return viaCepClient.buscarCep(cep);
    }
}