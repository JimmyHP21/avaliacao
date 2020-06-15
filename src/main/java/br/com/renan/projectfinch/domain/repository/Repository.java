package br.com.renan.projectfinch.domain.repository;

import br.com.renan.projectfinch.domain.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Interface padrão para implementação do padrão Repository.
 *
 * @author Renan Peres
 */
public interface Repository<T, ID> extends Serializable {

    /**
     * Consulta as entidades.
     *
     * @return
     */
    List<T> find(Query<T> query);

    /**
     * Consulta o total de entidades.
     *
     * @param query
     * @return
     */
    long count(Query<T> query);

    /**
     * Consulta uma entidade através do ID.
     *
     * @param id
     * @return
     */
    Optional<T> findById(ID id);

    /**
     * Insere uma nova entidade.
     *update
     * @param entity
     */
    void create(T entity);

    /**
     * Altera uma entidade.
     *
     * @param entity
     */
    void update(T entity);

    /**
     * Remove uma entidade.
     *
     * @param entity
     */
    void remove(T entity);

    /**
     * Remove uma entidade através do ID.
     *
     * @param id
     */
    void removeById(ID id);
}
