package br.com.ifba.prg04pizzly.pagamentos.controller;

import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoRequestDTO;
import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoResponseDTO;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.StatusPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//Interface dos endpoints da entidade pagamento
public interface PagamentoIController {

    // cadastrar um novo pagamento
    ResponseEntity<PagamentoResponseDTO> save(
            PagamentoRequestDTO pagamentoDTO);

    // lista pagamentos com paginação
    ResponseEntity<Page<PagamentoResponseDTO>> findAll(
            Pageable pageable);

    // buscar pagamento pelo id
    ResponseEntity<PagamentoResponseDTO> findById(
            Long id);

    // busca o pagamento associado a um pedido
    ResponseEntity<PagamentoResponseDTO> findByPedidoId(
            Long pedidoId);

    // atualiza os dados de um pagamento
    ResponseEntity<PagamentoResponseDTO> update(
            Long id,
            PagamentoRequestDTO pagamentoDTO);

    // atualiza apenas o status do pagamento
    ResponseEntity<PagamentoResponseDTO> updateStatus(
            Long id,
            StatusPagamento statusPagamento);

    // remove um pagamento pelo id
    ResponseEntity<Void> delete(
            Long id);
}