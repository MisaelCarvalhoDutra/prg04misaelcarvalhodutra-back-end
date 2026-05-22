package br.com.ifba.prg04pizzly.infrastructure.exceptions;

//exceção para regras de negócio da aplicação
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}