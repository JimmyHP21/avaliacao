package br.com.renan.projectfinch.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "BURGER")
public class Burger implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROMOTION")
    private String promotion;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "BURGER_INGREDIENTS",
            joinColumns = {@JoinColumn(name = "BURGER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "INGREDIENTS_ID")})
    private List<Ingredients> ingredients;

    @Transient
    @JsonIgnore
    int hamb = 0;

    @Transient
    @JsonIgnore
    int cheese = 0;

    // CONSTRUCTOR
    public Burger(Long id, Double price, String name, String promotion, List<Ingredients> ingredients) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.promotion = promotion;
        this.ingredients = new LinkedList<>();
        this.ingredients.addAll(ingredients);
    }


    @QueryProjection
    public Burger(Long id, Double price, String name,String promotion) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.promotion = promotion;
    }

    @QueryProjection
    public Burger(Double price, String promotion) {
        this.price = price;
        this.promotion = promotion;
        this.promotion = promotion;
    }

    @QueryProjection
    public Burger(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    @QueryProjection
    public Burger(String name, Double price, List<Ingredients> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = new LinkedList<>();
        this.ingredients.addAll(ingredients);
    }
    // FIM - CONSTRUCTOR

    //GETTERS E SETTERS

    public Double getPrice() {
        String promotion = getPromotion(getIngredients());
        this.getPriceTotal(getIngredients(), promotion);
        return this.price;
    }
    //FIM - GETTERS E SETTERS

    // METHODS UTILS
    private void getPriceTotal(List<Ingredients> ingredients, String promotion) {
        hamb = 0;
        cheese = 0;
        ingredients.forEach(ingredient -> {
            if (promotion.equals("lotOfMeat")) {
                if (ingredient.getName().toUpperCase().equals("HAMBÚRGUER")) {
                    hamb += 1;
                    if (hamb == 3) {
                        hamb = 0;
                    } else {
                        this.price += ingredient.getPrice();
                    }
                } else {
                    this.price += ingredient.getPrice();
                }
            } else if (promotion.equals("lotOfCheese")) {
                if (ingredient.getName().toUpperCase().equals("QUEIJO")) {
                    cheese += 1;
                    if (cheese == 3) {
                        cheese = 0;
                    } else {
                        this.price += ingredient.getPrice();
                    }
                } else {
                    this.price += ingredient.getPrice();
                }
            } else {
                this.price += ingredient.getPrice();
            }

        });

        if (promotion.toLowerCase().equals("ligth")) {
            this.price = this.price - ((10 * this.price) / 100);
        }
    }

    private String getPromotion(List<Ingredients> ingredients) {

        boolean alface = false;
        boolean bacon = false;
        int lotOfMeat = 0;
        int lotOfCheese = 0;
        String promotion = "";

        for (Ingredients ingredient : ingredients) {
            if (ingredient.getName().toUpperCase().equals("ALFACE")) {
                alface = true;
            } else if (ingredient.getName().toUpperCase().equals("BACON")) {
                bacon = true;
            }

            if (ingredient.getName().toUpperCase().equals("HAMBÚRGUER"))
                lotOfMeat += 1;

            if (ingredient.getName().toUpperCase().equals("QUEIJO"))
                lotOfCheese += 1;


            if (alface && !bacon) {
                promotion = "ligth";
            } else if (lotOfMeat > 2) {
                promotion = "lotOfMeat";
            } else if (lotOfCheese > 2) {
                promotion = "lotOfCheese";
            } else {
                promotion = "na";
            }

        }

        this.promotion = promotion;
        return promotion;
    }
    //FIM - METHODS UTILS
}
