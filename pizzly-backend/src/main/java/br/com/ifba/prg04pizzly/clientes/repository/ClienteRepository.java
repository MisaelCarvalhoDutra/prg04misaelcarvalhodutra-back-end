package br.com.ifba.prg04pizzly.clientes.repository;

import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório responsável pelas operações de persistência da entidade Cliente
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // valida se já existe cliente cadastrado com o email informado
    boolean existsByEmail(String email);

    // verifica se já existe outro cliente usando o mesmo e-mail durante a atualização
    boolean existsByEmailAndIdNot(String email, Long id);
}