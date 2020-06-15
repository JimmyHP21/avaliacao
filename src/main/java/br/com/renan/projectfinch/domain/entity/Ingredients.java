package br.com.renan.projectfinch.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "INGREDIENTS")
public class Ingredients implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "ingredients")
    private List<Burger> burgers;

    // CONSTRUCTOR
    public Ingredients() {

    }

    public Ingredients(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @QueryProjection
    public Ingredients(String name, Double price) {
        this.name = name;
        this.price = price;
    }
    // FIM - CONSTRUCTOR

}
