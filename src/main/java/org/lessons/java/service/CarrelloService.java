package org.lessons.java.service;

import org.lessons.java.model.Carrello;
import org.lessons.java.model.Pizza;
import org.lessons.java.model.User;
import org.lessons.java.repo.CarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    public Carrello getCarrelloByUser(User user) {
        return carrelloRepository.findByUtente(user);
    }

    public Carrello addPizzaToCarrello(User user, Pizza pizza) {
        Carrello carrello = carrelloRepository.findByUtente(user);
        if (carrello == null) {
            carrello = new Carrello();
            carrello.setUtente(user);
        }
        carrello.addPizza(pizza);
        return carrelloRepository.save(carrello);
    }

    public Carrello removePizzaFromCarrello(User user, Pizza pizza) {
        Carrello carrello = carrelloRepository.findByUtente(user);
        if (carrello != null) {
            carrello.removePizza(pizza);
            carrelloRepository.save(carrello);
        }
        return carrello;
    }
}
