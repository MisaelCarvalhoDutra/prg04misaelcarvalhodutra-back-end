package br.com.ifba.prg04pizzly.funcionarios.repository;

import br.com.ifba.prg04pizzly.usuarios.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

//Repositório responsável pelas operações de persistência da entidade Funcionário
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // aqui verifica se já existe um funcionário cadastrado com o e-mail informado
    boolean existsByEmail(String email);

    // verifica se outro funcionário já utiliza o mesmo e-mail durante a atualização
    boolean existsByEmailAndIdNot(String email, Long id);

    // verifica se já existe um funcionário cadastrado com a matrícula informada
    boolean existsByMatricula(String matricula);

    // verifica se outro funcionário já utiliza a mesma matrícula durante a atualização
    boolean existsByMatriculaAndIdNot(String matricula, Long id);
}