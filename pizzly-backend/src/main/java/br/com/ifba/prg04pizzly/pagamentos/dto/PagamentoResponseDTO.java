package br.com.ifba.prg04pizzly.pagamentos.dto;

import br.com.ifba.prg04pizzly.pagamentos.entity.enums.FormaPagamento;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.StatusPagamento;
import lombok.Data;

import java.math.BigDecimal;

//é o DTO utilizado para retornar pro front os dados do pagamento
@Data
public class PagamentoResponseDTO {

    private Long id;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
    private BigDecimal valorTroco;

    // identifica o pedido relacionado ao pagamento
    private Long pedidoId;
}