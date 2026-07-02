package br.com.ifba.prg04pizzly.funcionarios.dto;

import br.com.ifba.prg04pizzly.usuarios.entity.enums.Perfil;
import lombok.Data;

import java.time.LocalDateTime;

//DTO utilizado para devolver os dados do funcionário sem expor informações sensíveis, como a senha
@Data
public class FuncionarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String matricula;
    private Perfil perfil;
    private LocalDateTime dataCadastro;
    private Boolean ativo;
}