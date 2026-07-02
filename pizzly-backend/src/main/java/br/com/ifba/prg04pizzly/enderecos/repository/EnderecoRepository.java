package br.com.ifba.prg04pizzly.enderecos.repository;

import br.com.ifba.prg04pizzly.enderecos.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//esse repositório é responsável pelas operações de persistência de Endereço.
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    // lista todos os endereços pertencentes a um cliente
    List<Endereco> findByClienteId(Long clienteId);
    //Esse método é criado automaticamente pelo Spring Data JPA, busca todos os endereços vinculados a um cliente.
}