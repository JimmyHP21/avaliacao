package br.com.renan.projectfinch.domain.query.impl;
import br.com.renan.projectfinch.domain.entity.Burger;
import br.com.renan.projectfinch.domain.query.Filter;

/**
 * Created by Renan on 13/06/2020.
 */
public class BurgerFilter implements Filter<Burger> {

    private Long id;

    public BurgerFilter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
