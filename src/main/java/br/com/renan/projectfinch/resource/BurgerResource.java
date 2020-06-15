package br.com.renan.projectfinch.resource;

import br.com.renan.projectfinch.configuration.annotation.QueryParam;
import br.com.renan.projectfinch.domain.entity.Burger;
import br.com.renan.projectfinch.domain.query.Sorter;
import br.com.renan.projectfinch.domain.query.impl.BurgerFilter;
import br.com.renan.projectfinch.domain.service.BurgerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/burgers")
@Api(value = "Burgers")
public class BurgerResource {

    @Autowired
    private BurgerService burgerService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar todos os lanches", response = Burger.class)
    public ResponseEntity<List<Burger>> findAll(@QueryParam BurgerFilter burgerFilter,
                                                      @RequestParam(value = "sortBy", required = false) String sortProperty,
                                                      @RequestParam(value = "sortDirection", required = false) Sorter.Direction sortDirection,
                                                      @RequestParam(value = "page", required = false) Long page) {

        return ResponseEntity.ok(
                burgerService.find(burgerFilter, sortProperty, sortDirection, page)
        );
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Busca o lanche pelo id", response = Burger.class)
    public ResponseEntity<Burger> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(burgerService.findById(id));
    }

    @PostMapping(value = "/add",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Burger burger) {
        burgerService.create(burger);
        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(burger.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deleta o lanche do id que escolher")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        burgerService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
