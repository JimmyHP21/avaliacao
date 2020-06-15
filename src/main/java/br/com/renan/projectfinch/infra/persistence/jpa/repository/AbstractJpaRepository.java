package br.com.renan.projectfinch.infra.persistence.jpa.repository;
import br.com.renan.projectfinch.domain.exception.EntityNotFoundException;
import br.com.renan.projectfinch.domain.exception.IdNotNullException;
import br.com.renan.projectfinch.domain.exception.IdNullException;
import br.com.renan.projectfinch.domain.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

/**
 * Classe abstrata para implementação do padrão Repository utilizando a
 * especificação JPA (Java Persistence API).
 *
 * @author Renan Peres
 */
@SuppressWarnings("unchecked")
public abstract class AbstractJpaRepository<T, ID> implements Repository<T, ID> {

    protected final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Retorna se o ID da entidade é gerado automaticamente.
     *
     * @return
     */
    private boolean isGeneratedId() {
        return Arrays.stream(entityClass.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(GeneratedValue.class));
    }

    /**
     * Consulta uma entidade através do ID.
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    /**
     * Insere uma nova entidade.
     *
     * @param entity
     */
    @Override
    public void create(T entity) {
        Optional<ID> id = getIdentifier(entity);

        if (isGeneratedId() && id.isPresent()) {
            throw new IdNotNullException();
        } else if (!isGeneratedId() && id.isEmpty()) {
            throw new IdNullException();
        } else {
            entityManager.persist(entity);
            entityManager.flush();
        }
    }

    /**
     * Altera uma entidade.
     *
     * @param entity
     */
    @Override
    public void update(T entity) {
        Optional<ID> id = getIdentifier(entity);

        if (!id.isPresent()) {
            throw new IdNullException();
        } else {
            Optional.ofNullable(entityManager.find(entityClass, getIdentifier(entity).orElseThrow(IdNullException::new))).orElseThrow(EntityNotFoundException::new);
            entityManager.merge(entity);
            entityManager.flush();
        }
    }

    /**
     * Remove uma entidade.
     *
     * @param entity
     */
    @Override
    public void remove(T entity) {
        removeById(getIdentifier(entity).orElseThrow(IdNullException::new));
    }

    /**
     * Remove uma entidade através do ID.
     *
     * @param id
     */
    @Override
    public void removeById(ID id) {
        entityManager.remove(Optional.ofNullable(entityManager.find(entityClass, id)).orElseThrow(EntityNotFoundException::new));
        entityManager.flush();
    }

    /**
     * Retorna o ID da entidade.
     *
     * @param entity
     * @return
     */
    protected Optional<ID> getIdentifier(T entity) {
        return Optional.ofNullable((ID) entityManager
                .getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity));
    }
}
