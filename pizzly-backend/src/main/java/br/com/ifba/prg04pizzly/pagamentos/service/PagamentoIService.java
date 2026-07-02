package br.com.ifba.prg04pizzly.pagamentos.service;

import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoRequestDTO;
import br.com.ifba.prg04pizzly.pagamentos.dto.PagamentoResponseDTO;
import br.com.ifba.prg04pizzly.pagamentos.entity.enums.StatusPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//iInterface responsável pelos serviços da entidade pagamento
public interface PagamentoIService {

    // cadastra um novo pagamento
    PagamentoResponseDTO save(PagamentoRequestDTO pagamentoDTO);

    // listar pagamentos com paginação
    Page<PagamentoResponseDTO> findAll(Pageable pageable);

    // buscar pagamento pelo id
    PagamentoResponseDTO findById(Long id);

    // buscar pagamento associado a um pedido
    PagamentoResponseDTO findByPedidoId(Long pedidoId);

    // atualizar os dados de um pagamento
    PagamentoResponseDTO update(Long id, PagamentoRequestDTO pagamentoDTO);

    // atualizar apenas o status do pagamento
    PagamentoResponseDTO updateStatus(Long id, StatusPagamento statusPagamento);

    // remove um pagamento pelo id
    void delete(Long id);
}