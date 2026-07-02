package br.com.ifba.prg04pizzly.verificacaoemail.service;

import br.com.ifba.prg04pizzly.clientes.dto.ClienteRequestDTO;
import br.com.ifba.prg04pizzly.clientes.dto.ClienteResponseDTO;

//interface responsável pelos serviços de verificação por e-mail
public interface VerificacaoEmailIService {

    // envia código para validar e-mail no cadastro
    void enviarCodigoCadastro(String email);

    // valida o código e cria a conta do cliente
    ClienteResponseDTO validarCadastro(
            String email,
            String codigo,
            ClienteRequestDTO clienteDTO
    );

    // envia código para recuperação de senha
    void enviarCodigoRecuperacaoSenha(String email);

    // valida o código e redefine a senha do usuário
    void redefinirSenha(
            String email,
            String codigo,
            String novaSenha
    );
}