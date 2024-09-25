package org.lessons.java.service;

import java.util.ArrayList;

import org.lessons.java.model.Carrello;
import org.lessons.java.model.Ordine;
import org.lessons.java.model.Pizza;
import org.lessons.java.model.User;
import org.lessons.java.repo.CarrelloRepository;
import org.lessons.java.repo.OrdineRepository;
import org.lessons.java.repo.UserRepository;
import org.lessons.java.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;
    
    @Autowired
    private OrdineRepository ordineRepository;
    
 
    

    public Carrello getCarrelloByUser(User user) {
        return carrelloRepository.findByUtente(user);
    }

    public Carrello addPizzaToCarrello(User user, Pizza pizza) {
        Carrello carrello = carrelloRepository.findByUtente(user);
        if (carrello == null) {
            carrello = new Carrello();
            carrello.setUtente(user);
            carrello.setPizze(new ArrayList<>());
        }
        carrello.addPizza(pizza);
        return carrelloRepository.save(carrello);
    }

    public void removePizzaFromCarrello(User user, Integer pizzaId) {
    	
        Carrello carrello = carrelloRepository.findByUtente(user);
        if (carrello != null) {
        	System.out.println("Carrello trovato per l'utente: " + user.getUsername());
            // Cambia il nome del parametro della lambda da "pizza" a "p"
            Pizza pizzaToRemove = carrello.getPizze().stream()
                .filter(p -> p.getId().equals(pizzaId))  // Usa "p" invece di "pizza" per evitare il conflitto
                .findFirst()
                .orElse(null);

            if (pizzaToRemove != null) {
            	 System.out.println("Pizza trovata e rimossa: " + pizzaToRemove.getName() + " con ID: " + pizzaId);
                carrello.getPizze().remove(pizzaToRemove);
                carrelloRepository.save(carrello);  // Salva le modifiche nel database
            }else {
                System.out.println("Pizza non trovata con ID: " + pizzaId);
            }
        } else {
        	System.out.println("Carrello non trovato");
        }
    }

    
    public void confermaOrdine(User user) {
        Carrello carrello = carrelloRepository.findByUtente(user);
        if (carrello != null && !carrello.getPizze().isEmpty()) {
            Ordine ordine = new Ordine();
            ordine.setUtente(user);
            ordine.setPizze(carrello.getPizze());

            ordineRepository.save(ordine);

            // Pulisci il carrello dopo aver creato l'ordine
            carrello.getPizze().clear();
            carrelloRepository.save(carrello);
        }
    }
}
