package br.com.ifba.prg04pizzly.enderecos.service;

import br.com.ifba.prg04pizzly.clientes.repository.ClienteRepository;
import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoRequestDTO;
import br.com.ifba.prg04pizzly.enderecos.dto.EnderecoResponseDTO;
import br.com.ifba.prg04pizzly.enderecos.entity.Endereco;
import br.com.ifba.prg04pizzly.enderecos.repository.EnderecoRepository;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.ResourceNotFoundException;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//implementação das regras de negócio da entidade endereço
@Service
@RequiredArgsConstructor
public class EnderecoService implements EnderecoIService {

    // repositório responsável pela persistência dos endereços
    private final EnderecoRepository enderecoRepository;

    // repositório responsável pela persistência dos clientes
    private final ClienteRepository clienteRepository;

    // responsável pela conversão entre DTOs e entidades
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public EnderecoResponseDTO save(EnderecoRequestDTO enderecoDTO) {

        // busca o cliente proprietário do endereço
        Cliente cliente = clienteRepository.findById(enderecoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // se o novo endereço for principal, desmarca os demais endereços do cliente (só um pode ser principal)
        if (Boolean.TRUE.equals(enderecoDTO.getPrincipal())) {
            List<Endereco> enderecosDoCliente =
                    enderecoRepository.findByClienteId(cliente.getId());

            enderecosDoCliente.forEach(endereco ->
                    endereco.setPrincipal(false));

            enderecoRepository.saveAll(enderecosDoCliente);
        }

        // cria a entidade manualmente para vincular corretamente o cliente ao endereço
        Endereco endereco = new Endereco();

        endereco.setTipo(enderecoDTO.getTipo());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setUf(enderecoDTO.getUf());
        endereco.setPrincipal(enderecoDTO.getPrincipal());
        endereco.setCliente(cliente);

        Endereco salvo = enderecoRepository.save(endereco);

        return converterParaResponseDTO(salvo);
    }

    @Override
    public Page<EnderecoResponseDTO> findAll(Pageable pageable) {

        // busca todos os endereços de forma paginada e converte para DTO de resposta
        return enderecoRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {

        // busca o endereço pelo id ou lança exceção caso não exista
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endereço não encontrado")); //exceção

        return converterParaResponseDTO(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> findByClienteId(Long clienteId) {

        // busca todos os endereços vinculados ao cliente informado
        return enderecoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(
            Long id,
            EnderecoRequestDTO enderecoDTO) {

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endereço não encontrado"));

        Cliente cliente = clienteRepository.findById(enderecoDTO.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente não encontrado"));

        // se o endereço atualizado for principal, desmarca os demais endereços do cliente
        if (Boolean.TRUE.equals(enderecoDTO.getPrincipal())) {
            List<Endereco> enderecosDoCliente =
                    enderecoRepository.findByClienteId(cliente.getId());

            enderecosDoCliente.forEach(enderecoCliente ->
                    enderecoCliente.setPrincipal(false));

            enderecoRepository.saveAll(enderecosDoCliente);
        }

        endereco.setTipo(enderecoDTO.getTipo());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setUf(enderecoDTO.getUf());
        endereco.setPrincipal(enderecoDTO.getPrincipal());
        endereco.setCliente(cliente);

        Endereco atualizado = enderecoRepository.save(endereco);

        return converterParaResponseDTO(atualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // busca o endereço antes de remover para garantir que ele existe
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endereço não encontrado"));

        enderecoRepository.delete(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO definirPrincipal(Long id) {

        Endereco enderecoPrincipal = enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endereço não encontrado"));

        Long clienteId = enderecoPrincipal.getCliente().getId();

        List<Endereco> enderecos =
                enderecoRepository.findByClienteId(clienteId);
        // remove a marcação de principal dos outros endereços
        enderecos.forEach(endereco -> endereco.setPrincipal(false));

        // marca o endereço escolhido como principal
        enderecoPrincipal.setPrincipal(true);

        enderecoRepository.saveAll(enderecos);
        Endereco atualizado = enderecoRepository.save(enderecoPrincipal);

        return converterParaResponseDTO(atualizado);
    }

    //Converte entidade para DTO de resposta
    private EnderecoResponseDTO converterParaResponseDTO(Endereco endereco) {

        EnderecoResponseDTO dto =
                modelMapper.map(endereco, EnderecoResponseDTO.class);

        dto.setClienteId(endereco.getCliente().getId());
        dto.setClienteNome(endereco.getCliente().getNome());

        return dto;
    }
}