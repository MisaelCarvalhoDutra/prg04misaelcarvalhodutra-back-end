package br.com.ifba.prg04pizzly.auth.service;

import br.com.ifba.prg04pizzly.auth.dto.GoogleLoginRequestDTO;
import br.com.ifba.prg04pizzly.auth.dto.LoginRequestDTO;
import br.com.ifba.prg04pizzly.auth.dto.LoginResponseDTO;
import br.com.ifba.prg04pizzly.infrastructure.exceptions.BusinessException;
import br.com.ifba.prg04pizzly.usuarios.entity.Cliente;
import br.com.ifba.prg04pizzly.usuarios.entity.Funcionario;
import br.com.ifba.prg04pizzly.usuarios.entity.Usuario;
import br.com.ifba.prg04pizzly.usuarios.repository.UsuarioRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

//Service responsável pelas regras de autenticação do sistema.
//realiza login tradicional e login com Google.
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    @Value("${google.client.id}")
    private String googleClientId;

    //Autentica o usuário pelo email e senha.
    public LoginResponseDTO login(LoginRequestDTO loginDTO) {

        // busca o usuário pelo e-mail informado
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Email ou senha inválidos"));

        // compara a senha informada com a senha salva no banco
        if (!usuario.getSenha().equals(loginDTO.getSenha())) {
            throw new BusinessException("Email ou senha inválidos");
        }

        // impede login de usuários inativos
        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new BusinessException(
                    "Sua conta está temporariamente desativada. Entre em contato com um administrador."
            );
        }

        return converterParaResponseDTO(usuario);
    }

    //Autentica o usuário com a conta Google.
    //Se o e-mail ainda não existir no sistema, cria um cliente automaticamente
    public LoginResponseDTO loginComGoogle(GoogleLoginRequestDTO googleDTO) {

        try {
            // configura o verificador do token utilizando o Client ID da aplicação
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            // valida o token recebido do frontend
            GoogleIdToken idToken = verifier.verify(googleDTO.getCredential());

            if (idToken == null) {
                throw new BusinessException("Token do Google inválido");
            }

            GoogleIdToken.Payload payload = idToken.getPayload(); //é a parte do token do Google que contém os dados
            // do usuário autenticado,como nome e e-mail. Após validar o token, utilizamos getPayload() para extrair
            // essas informações e identificar ou criar o usuário no sistema

            // obtém os dados da conta Google
            String email = payload.getEmail();
            String nome = (String) payload.get("name");

            // procura o usuário pelo e-mail ou cria um novo cliente automaticamente
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseGet(() -> criarClienteGoogle(nome, email));

            // impede login de usuários inativos
            if (Boolean.FALSE.equals(usuario.getAtivo())) {
                throw new BusinessException(
                        "Sua conta está temporariamente desativada. Entre em contato com um administrador."
                );
            }

            return converterParaResponseDTO(usuario);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Erro ao autenticar com Google");
        }
    }

    //Cria automaticamente um cliente quando o login com Google
    //é realizado por um e-mail ainda não cadastrado no sistema
    private Usuario criarClienteGoogle(String nome, String email) {

        Cliente cliente = new Cliente();

        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setTelefone("");
        cliente.setSenha("LOGIN_GOOGLE"); // contas criadas pelo Google não possuem senha tradicional
        cliente.setDataCadastro(LocalDateTime.now());
        cliente.setAtivo(true);

        return usuarioRepository.save(cliente);
    }


    //Converte o usuário autenticado para DTO de resposta
    private LoginResponseDTO converterParaResponseDTO(Usuario usuario) {

        LoginResponseDTO dto = new LoginResponseDTO();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());

        // identifica se o usuário autenticado é um cliente
        if (usuario instanceof Cliente) {
            dto.setTipo("CLIENTE");
            dto.setPerfil("CLIENTE");
        }

        // identifica se o usuário autenticado é um funcionário
        if (usuario instanceof Funcionario funcionario) {
            dto.setTipo("FUNCIONARIO");
            dto.setPerfil(funcionario.getPerfil().name());
        }

        return dto;
    }
}