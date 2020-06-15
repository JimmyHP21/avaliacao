package br.com.renan.projectfinch.infra.persistence.jpa.repository;

import br.com.renan.projectfinch.domain.entity.Burger;
import br.com.renan.projectfinch.domain.entity.QBurger;
import br.com.renan.projectfinch.domain.query.Query;
import br.com.renan.projectfinch.domain.query.impl.BurgerFilter;
import br.com.renan.projectfinch.domain.repository.BurgerRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repositório da entidade Burger
 *
 * @author Renan Peres
 */
@Repository
public class BurgerJpaRepository extends AbstractJpaRepository<Burger, Long> implements BurgerRepository {

    @Override
    public List<Burger> find(Query<Burger> query) {
         return this.createQuery(query, Boolean.FALSE).fetch();
    }

    private JPAQuery<Burger> createQuery(Query<Burger> query, Boolean sortAndPaging) {
        QBurger qBurger = QBurger.burger;

        final PathBuilder<Burger> path = new PathBuilder<>(Burger.class, "burger");

        final JPAQuery<Burger> jpaQuery = new JPAQueryFactory(entityManager)
                .select(
                        qBurger
                )
                .from(qBurger);

        if (Objects.nonNull(query)) {
            if (query.getFilter() != null && query.getFilter() instanceof BurgerFilter) {
                final BurgerFilter filter = (BurgerFilter) query.getFilter();

                if (Objects.nonNull(filter.getId())) {
                    jpaQuery.where(qBurger.id.eq(filter.getId()));
                }
            }
        }

        //Utilizado para paginar
        if (sortAndPaging) {
            if (query.getSorter() != null && StringUtils.isNotBlank(query.getSorter().getProperty())) {
                if (query.getSorter().isDesc()) {
                    jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).desc());
                } else {
                    jpaQuery.orderBy(path.getString(query.getSorter().getProperty()).asc());
                }
            } else {
                jpaQuery.orderBy(path.getString("id").asc());
            }

            // page
            if (Optional.ofNullable(query.getPage()).orElse(0L) > 0L) {
                jpaQuery.offset(((query.getPage() - 1L) * query.getLimit()));
            }

            // limit
            jpaQuery.limit(query.getLimit());
        }

        return jpaQuery;
    }

    @Override
    public long count(Query<Burger> query) {
        return 0;
    }
}
