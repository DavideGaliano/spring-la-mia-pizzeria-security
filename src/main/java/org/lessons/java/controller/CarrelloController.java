package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.Carrello;
import org.lessons.java.model.Ordine;
import org.lessons.java.model.User;
import org.lessons.java.repo.UserRepository;
import org.lessons.java.security.CustomUserDetailsService;
import org.lessons.java.service.CarrelloService;
import org.lessons.java.service.OrdineService;
import org.lessons.java.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

	@Autowired
	private OrdineService ordineService;
	
	@Autowired
    private CustomUserDetailsService userService;

	@GetMapping
	 public String viewCarrello(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		//identifico l'utente
		User user = userService.findByUsername(userDetails.getUsername());
		//identifico il carrello in base all'utente
		Carrello carrello = carrelloService.getCarrelloByUser(user);
		
		model.addAttribute("carrello", carrello);
		
		 return "carrello/carrello";
	 }

	@GetMapping("/order-history")
	public String orderList(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = ordineService.findUserByUsername(userDetails.getUsername());
		List<Ordine> ordini = ordineService.getOrdiniByUser(user);
		model.addAttribute("ordini", ordini);
		return "carrello/order-history";
	}

	@PostMapping("/conferma")
	public String confermaOrdine(@AuthenticationPrincipal UserDetails userDetails) {
		User user = ordineService.findUserByUsername(userDetails.getUsername());
		carrelloService.confermaOrdine(user);
		return "redirect:/carrello";
	}

	@PostMapping("/remove/{id}")
	public String removePizzaFromCarrello(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
	    // Controlla se userDetails contiene un username valido
	    if (userDetails != null) {
	        System.out.println("Username dell'utente autenticato: " + userDetails.getUsername());
	    } else {
	        System.out.println("UserDetails è null!");
	    }

	    // Recupera l'utente autenticato dal database
	    User user = userService.findByUsername(userDetails.getUsername());

	    if (user != null) {
	        System.out.println("Utente recuperato: " + user.getUsername());
	    } else {
	        System.out.println("Utente non trovato per l'username: " + userDetails.getUsername());
	    }

	    // Rimuovi la pizza dal carrello
	    carrelloService.removePizzaFromCarrello(user, id);
		return "redirect:/carrello";
	}
}
