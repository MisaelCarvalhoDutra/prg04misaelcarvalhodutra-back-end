package br.com.ifba.prg04pizzly.pagamentos.dto;

import br.com.ifba.prg04pizzly.pagamentos.entity.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

//DTO utilizado para cadastro e atualização de pagamentos
@Data
public class PagamentoRequestDTO {

    @NotNull(message = "a forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento; //enum com as formas de pagamento

    // valor informado pelo cliente quando o pagamento for em dinheiro
    private BigDecimal valorTroco;

    // pedido ao qual este pagamento pertence
    @NotNull(message = "o pedido é obrigatório")
    private Long pedidoId;
}