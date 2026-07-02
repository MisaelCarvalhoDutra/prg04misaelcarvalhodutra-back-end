package br.com.ifba.prg04pizzly.entregas.dto;

import br.com.ifba.prg04pizzly.entregas.entity.enums.FormaRecebimento;
import lombok.Data;

//DTO utilizado para devolver os dados da entrega ao frontend
@Data
public class EntregaResponseDTO {

    private Long id;
    private FormaRecebimento formaRecebimento;
    private String horarioPreferido;
    private String tempoEstimado;
    private String observacao;

    //evitam devolver os objetos completos de Pedido e Endereco
    private Long pedidoId;
    private Long enderecoId;
}