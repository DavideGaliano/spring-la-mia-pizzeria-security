package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.OffertaSpeciale;
import org.lessons.java.model.Pizza;
import org.lessons.java.service.OffertaSpecialeService;
import org.lessons.java.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OfferteSpecialiController {
	
	@Autowired
    private OffertaSpecialeService offertaSpecialeService;
	
	@Autowired
    private PizzaService pizzaService;
	
	// Mostra il form di creazione di una nuova offerta
    @GetMapping("/create")
    public String showCreateForm(Model model) {
    	List<Pizza> pizze = pizzaService.findAll();  // Recupera tutte le pizze
        model.addAttribute("pizze", pizze);          // Passa la lista delle pizze al modello
        model.addAttribute("offertaSpeciale", new OffertaSpeciale());
        return "offerte/create";
    }
    
    @PostMapping("/create")
    public String createOfferta(@Valid @ModelAttribute("offertaSpeciale") OffertaSpeciale offertaSpeciale, 
                                @RequestParam("pizzaId") Integer pizzaId, 
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<Pizza> pizze = pizzaService.findAll();  // Recupera le pizze se ci sono errori per ripopolare la dropdown
            model.addAttribute("pizze", pizze);
            return "offerte/create";
        }

        // Recupera la pizza selezionata
        Pizza pizza = pizzaService.findById(pizzaId);

        // Associa la pizza all'offerta
        offertaSpeciale.setPizza(pizza);

        // Salva l'offerta
        offertaSpecialeService.save(offertaSpeciale);

        return "redirect:/pizze/" + pizza.getId();  // Reindirizza alla pagina della pizza
    }


}
