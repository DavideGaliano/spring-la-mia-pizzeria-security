package org.lessons.java.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "carrello_pizze",
        joinColumns = @JoinColumn(name = "carrello_id"),
        inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizze = new ArrayList<>();  // Inizializza la lista delle pizze

    @OneToOne
    private User utente;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Pizza> getPizze() {
        return pizze;
    }

    public void setPizze(List<Pizza> pizze) {
        this.pizze = pizze;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }

 // Metodo per aggiungere una pizza al carrello
    public void addPizza(Pizza pizza) {
        if (pizze == null) {
            pizze = new ArrayList<>();  // Inizializza la lista se è null
        }
        this.pizze.add(pizza);
    }

    public void removePizza(Pizza pizza) {
        if (pizze != null) {
            this.pizze.remove(pizza);
        }
    }
}
