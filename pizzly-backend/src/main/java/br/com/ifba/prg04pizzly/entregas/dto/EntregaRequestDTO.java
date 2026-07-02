package br.com.ifba.prg04pizzly.entregas.dto;

import br.com.ifba.prg04pizzly.entregas.entity.enums.FormaRecebimento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//DTO utilizado para cadastro e atualização de entregas
@Data
public class EntregaRequestDTO {

    @NotNull(message = "a forma de recebimento é obrigatória")
    private FormaRecebimento formaRecebimento;

    private String horarioPreferido;

    private String tempoEstimado;

    private String observacao;

    @NotNull(message = "o pedido é obrigatório")
    private Long pedidoId;

    // endereço utilizado quando a forma de recebimento for entrega
    private Long enderecoId;
}