package br.com.renan.projectfinch.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @autor Renan Peres.
 *
 * Classe padrão para tratamento de erros IdNull
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IdNullException extends RuntimeException {
    public IdNullException() {
        super("Campo ID está vazio!");
    }
}
