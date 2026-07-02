package br.com.ifba.prg04pizzly.entregas.service;

import br.com.ifba.prg04pizzly.enderecos.entity.Endereco;
import br.com.ifba.prg04pizzly.enderecos.repository.EnderecoRepository;
import br.com.ifba.prg04pizzly.entregas.dto.EntregaRequestDTO;
import br.com.ifba.prg04pizzly.entregas.dto.EntregaResponseDTO;
import br.com.ifba.prg04pizzly.entregas.entity.Entrega;
import br.com.ifba.prg04pizzly.entregas.repository.EntregaRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.pedidos.entity.Pedido;
import br.com.ifba.prg04pizzly.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//implementação das regras de negócio de entrega
@Service
@RequiredArgsConstructor
public class EntregaService implements EntregaIService {

    // repositório responsável pela persistência das entregas
    private final EntregaRepository entregaRepository;

    // repositório responsável pela busca dos pedidos vinculados às entregas
    private final PedidoRepository pedidoRepository;

    // repositório responsável pela busca dos endereços utilizados nas entregas
    private final EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public EntregaResponseDTO save(EntregaRequestDTO entregaDTO) {

        // busca o pedido que será vinculado à entrega
        Pedido pedido = pedidoRepository.findById(entregaDTO.getPedidoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido não encontrado"));

        // garante que um pedido tenha apenas uma entrega cadastrada
        if (entregaRepository.existsByPedidoId(entregaDTO.getPedidoId())) {
            throw new BusinessException("Este pedido já possui entrega cadastrada");
        }

        // cria a entrega com os dados informados
        Entrega entrega = new Entrega();

        entrega.setFormaRecebimento(entregaDTO.getFormaRecebimento());
        entrega.setHorarioPreferido(entregaDTO.getHorarioPreferido());
        entrega.setTempoEstimado(entregaDTO.getTempoEstimado());
        entrega.setObservacao(entregaDTO.getObservacao());
        entrega.setPedido(pedido);

        // busca o endereço apenas quando ele for informado
        if (entregaDTO.getEnderecoId() != null) {

            Endereco endereco = enderecoRepository.findById(
                            entregaDTO.getEnderecoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Endereço não encontrado"));

            entrega.setEndereco(endereco);
        }

        Entrega salva = entregaRepository.save(entrega);

        return converterParaResponseDTO(salva);
    }

    @Override
    public Page<EntregaResponseDTO> findAll(Pageable pageable) {

        // busca todas as entregas de forma paginada e converte para DTO de resposta
        return entregaRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public EntregaResponseDTO findById(Long id) {

        // busca a entrega pelo id ou lança exceção caso não exista
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entrega não encontrada")); //caso não encotre

        return converterParaResponseDTO(entrega);
    }

    @Override
    public EntregaResponseDTO findByPedidoId(Long pedidoId) {

        // busca a entrega vinculada ao pedido informado
        Entrega entrega = entregaRepository.findByPedidoId(pedidoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entrega não encontrada"));

        return converterParaResponseDTO(entrega);
    }

    @Override
    @Transactional
    public EntregaResponseDTO update(
            Long id,
            EntregaRequestDTO entregaDTO) {

        // busca a entrega que será atualizada
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entrega não encontrada"));

        // atualiza os dados da entrega
        entrega.setFormaRecebimento(entregaDTO.getFormaRecebimento());
        entrega.setHorarioPreferido(entregaDTO.getHorarioPreferido());
        entrega.setTempoEstimado(entregaDTO.getTempoEstimado());
        entrega.setObservacao(entregaDTO.getObservacao());

        // atualiza o endereço quando informado; se for retirada, remove o vínculo
        if (entregaDTO.getEnderecoId() != null) {

            Endereco endereco = enderecoRepository.findById(
                            entregaDTO.getEnderecoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Endereço não encontrado"));

            entrega.setEndereco(endereco);
        } else {

            entrega.setEndereco(null);
        }

        Entrega atualizada = entregaRepository.save(entrega);

        return converterParaResponseDTO(atualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca a entrega antes de remover para garantir que ela existe
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entrega não encontrada"));

        entregaRepository.delete(entrega);
    }

    //converte entidade para DTO de resposta
    private EntregaResponseDTO converterParaResponseDTO(
            Entrega entrega) {

        EntregaResponseDTO dto = new EntregaResponseDTO();

        dto.setId(entrega.getId());
        dto.setFormaRecebimento(entrega.getFormaRecebimento());
        dto.setHorarioPreferido(entrega.getHorarioPreferido());
        dto.setTempoEstimado(entrega.getTempoEstimado());
        dto.setObservacao(entrega.getObservacao());

        dto.setPedidoId(entrega.getPedido().getId());

        // adiciona o endereço somente se a entrega possuir endereço vinculado
        if (entrega.getEndereco() != null) {
            dto.setEnderecoId(entrega.getEndereco().getId());
        }

        return dto;
    }
}