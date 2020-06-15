package br.com.renan.projectfinch.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Tratamento de Exceções Genéricas
 *
 * @author Renan Peres
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GenericErrorException extends RuntimeException {
    public GenericErrorException(String mensagem) {
        super(mensagem);
    }
}
