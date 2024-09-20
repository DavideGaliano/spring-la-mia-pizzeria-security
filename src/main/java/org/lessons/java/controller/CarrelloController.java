package org.lessons.java.controller;

import org.lessons.java.model.User;
import org.lessons.java.service.CarrelloService;
import org.lessons.java.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carrello")
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;
    
	@Autowired
    private PizzaService pizzaService;

    @GetMapping
    public String viewCarrello(Model model, User user) {
        model.addAttribute("carrello", carrelloService.getCarrelloByUser(user));
        return "carrello";
    }

    @PostMapping("/remove/{id}")
    public String removeFromCarrello(@PathVariable("id") Integer pizzaId, User user) {
        carrelloService.removePizzaFromCarrello(user, pizzaService.findById(pizzaId));
        return "redirect:/carrello";
    }
}
