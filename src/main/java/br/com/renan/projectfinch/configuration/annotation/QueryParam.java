package br.com.renan.projectfinch.configuration.annotation;

import java.lang.annotation.*;

/**
 * Classe para filtros (end-points) das entidades
 *
 * @author Renan Peres.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
}
