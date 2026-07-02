package br.com.ifba.prg04pizzly.entregas.controller;

import br.com.ifba.prg04pizzly.entregas.dto.EntregaRequestDTO;
import br.com.ifba.prg04pizzly.entregas.dto.EntregaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

//interface dos endpoints de entrega,define o contrato das operações HTTP disponíveis para entregas
// */
public interface EntregaIController {

    // cadastra uma nova entrega
    ResponseEntity<EntregaResponseDTO> save(
            EntregaRequestDTO entregaDTO);

    // lista entregas com paginação
    ResponseEntity<Page<EntregaResponseDTO>> findAll(
            Pageable pageable);

    // buscar entrega pelo id
    ResponseEntity<EntregaResponseDTO> findById(
            Long id);

    // buscar entrega vinculada a um pedido
    ResponseEntity<EntregaResponseDTO> findByPedidoId(
            Long pedidoId);

    // atualiza os dados de uma entrega
    ResponseEntity<EntregaResponseDTO> update(
            Long id,
            EntregaRequestDTO entregaDTO);

    // remove uma entrega pelo id
    ResponseEntity<Void> delete(
            Long id);
}