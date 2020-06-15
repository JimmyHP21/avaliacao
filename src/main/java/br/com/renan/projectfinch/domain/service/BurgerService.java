package br.com.renan.projectfinch.domain.service;

import br.com.renan.projectfinch.domain.entity.Burger;
import br.com.renan.projectfinch.domain.exception.EntityNotFoundException;
import br.com.renan.projectfinch.domain.query.Query;
import br.com.renan.projectfinch.domain.query.Sorter;
import br.com.renan.projectfinch.domain.query.impl.BurgerFilter;
import br.com.renan.projectfinch.domain.repository.BurgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Renan on 13/06/2020.
 */
@Service
public class BurgerService implements Serializable {

    @Autowired
    private BurgerRepository burgerRepository;

    public List<Burger> find(BurgerFilter burgerFilter,
                             String sortProperty,
                             Sorter.Direction sortDirection,
                             Long page) {
        //Constrói a query para a entidade Burger
        final Query<Burger> query = Query.<Burger>builder()
                .filter(burgerFilter)
                .sort(Sorter.<Burger>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();

        return burgerRepository.find(query);
    }

    public Burger findById(Long id) {
        return burgerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(Burger burger) {
        burgerRepository.create(burger);
    }

    @Transactional
    public void update(Burger burger) {
        burgerRepository.update(burger);
    }

    @Transactional
    public void remove(Long idPessoa) {
        burgerRepository.removeById(idPessoa);
    }
}
