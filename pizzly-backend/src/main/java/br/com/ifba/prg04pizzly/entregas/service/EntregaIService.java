package br.com.ifba.prg04pizzly.entregas.service;

import br.com.ifba.prg04pizzly.entregas.dto.EntregaRequestDTO;
import br.com.ifba.prg04pizzly.entregas.dto.EntregaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//interface responsável pelos serviços de entrega
public interface EntregaIService {

    // cadastra uma nova entrega
    EntregaResponseDTO save(EntregaRequestDTO entregaDTO);

    // listar entregas com paginação
    Page<EntregaResponseDTO> findAll(Pageable pageable);

    // buscar entrega pelo id
    EntregaResponseDTO findById(Long id);

    // busca a entrega vinculada a um pedido
    EntregaResponseDTO findByPedidoId(Long pedidoId);

    // atualiza os dados de uma entrega
    EntregaResponseDTO update(Long id, EntregaRequestDTO entregaDTO);

    // remove uma entrega pelo id
    void delete(Long id);
}