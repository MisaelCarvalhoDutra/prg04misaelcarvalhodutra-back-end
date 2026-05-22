package br.com.ifba.prg04pizzly.infrastructure.exceptions;

// Exceção customizada para recursos não encontrados
// Utilizada pelo serviço quando um usuário ou outro recurso não existe no banco
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}