package org.lessons.java.service;

import org.lessons.java.model.Pizza;
import org.lessons.java.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public Pizza findById(Integer id) {
        return pizzaRepository.findById(id).get();
    }
    
    public List<Pizza> findByName(String name) {
    	return pizzaRepository.findByNameContainingIgnoreCase(name);
    }

    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public void deleteById(Integer id) {
        pizzaRepository.deleteById(id);
    }
}
