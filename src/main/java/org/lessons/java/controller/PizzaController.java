package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.Ingrediente;
import org.lessons.java.model.OffertaSpeciale;
import org.lessons.java.model.Pizza;
import org.lessons.java.model.User;
import org.lessons.java.repo.PizzaRepository;
import org.lessons.java.security.CustomUserDetailsService;
import org.lessons.java.service.CarrelloService;
import org.lessons.java.service.IngredienteService;
import org.lessons.java.service.OffertaSpecialeService;
import org.lessons.java.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizze")
public class PizzaController {
	
	@Autowired
    private PizzaService pizzaService;

    @Autowired
    private OffertaSpecialeService offertaSpecialeService;
    
    @Autowired
    private IngredienteService ingredienteService;
    
    @Autowired
    private CarrelloService carrelloService;
    
    @Autowired
    private CustomUserDetailsService userService;
	
	//READ
	
	@GetMapping()
    public String index(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Pizza> pizze;
        if (search != null && !search.isEmpty()) {
            pizze = pizzaService.findByName(search);
        } else {
            pizze = pizzaService.findAll();  // Recupera tutte le pizze
        }
        
        model.addAttribute("pizze", pizze);
        model.addAttribute("search", search);  // Mantieni il valore di ricerca nel modello
        
        return "/pizze/index";
    }
	
	//READ PIZZE ORDER
	@GetMapping("/index-order")
    public String indexOrder(@RequestParam(name = "search", required = false) String search, Model model) {
        List<Pizza> pizze;
        if (search != null && !search.isEmpty()) {
            pizze = pizzaService.findByName(search);
        } else {
            pizze = pizzaService.findAll();  // Recupera tutte le pizze
        }
        
        model.addAttribute("pizze", pizze);
        model.addAttribute("search", search);  // Mantieni il valore di ricerca nel modello
        
        return "/pizze/index-order";
    }
	
	// Aggiungi la pizza al carrello
    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable("id") Integer pizzaId, @AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes attributes) {
        Pizza pizza = pizzaService.findById(pizzaId);
        
     // Recupera l'utente autenticato
        User user = userService.findByUsername(userDetails.getUsername());
        
     // Aggiungi la pizza al carrello dell'utente
        carrelloService.addPizzaToCarrello(user, pizza);
        attributes.addFlashAttribute("successMessage", "Pizza con id "+ pizzaId +" è stata aggiunta al carrello");
        return "redirect:/pizze/index-order";
    }

	
	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = pizzaService.findById(id);
        model.addAttribute("pizza", pizza);
        model.addAttribute("offerteSpeciali", pizza.getOfferteSpeciali());
        model.addAttribute("ingredienti", pizza.getIngredienti());
        return "pizze/show";
    }
	
	//SHOW PIZZE ORDINE
	@GetMapping("/index-order/{id}")
	public String showOrder(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("pizza", pizzaService.findById(id));
		return "/pizze/show-order";
	}
    
    // Salva una nuova offerta speciale
    @PostMapping("/{id}/offerte")
    public String createOfferta(@PathVariable("id") Integer pizzaId, @Valid OffertaSpeciale offertaSpeciale) {
        Pizza pizza = pizzaService.findById(pizzaId);

        // Aggiunge l'offerta speciale alla lista delle offerte della pizza
        offertaSpeciale.setPizza(pizza);

        offertaSpecialeService.save(offertaSpeciale);
        return "redirect:/pizze/" + pizzaId;
    }


    // Modifica un'offerta speciale esistente
    @GetMapping("/offerte/{offertaId}/edit")
    public String showEditOffertaForm(@PathVariable("offertaId") Integer offertaId, Model model) {
        OffertaSpeciale offertaSpeciale = offertaSpecialeService.findById(offertaId);
        model.addAttribute("offertaSpeciale", offertaSpeciale);
        return "offerte/edit";
    }

    @PostMapping("/offerte/{offertaId}/edit")
    public String updateOfferta(@PathVariable("offertaId") Integer offertaId, @Valid OffertaSpeciale offertaSpeciale) {
        OffertaSpeciale existingOfferta = offertaSpecialeService.findById(offertaId);
        existingOfferta.setTitolo(offertaSpeciale.getTitolo());
        existingOfferta.setDataInizio(offertaSpeciale.getDataInizio());
        existingOfferta.setDataFine(offertaSpeciale.getDataFine());
        offertaSpecialeService.save(existingOfferta);
        return "redirect:/pizze/" + existingOfferta.getPizza().getId();
    }
	
	//CREATE
	
	@GetMapping("/create")
	public String create(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		model.addAttribute("pizza", new Pizza());
		return "/pizze/create";
		
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza ,@RequestParam("ingredienti") List<Integer> ingredientiId, BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()) {
			return "/pizze/create";
		}
		 List<Ingrediente> ingredienti = ingredienteService.findAllById(ingredientiId);
		 formPizza.setIngredienti(ingredienti);
		pizzaService.save(formPizza);
		
		return "redirect:/pizze";
	}
	
	//UPDATE
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("pizza", pizzaService.findById(id));
		
		return "/pizze/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza formPizza ,BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()) {
			return "/pizze/edit";
		}
		pizzaService.save(formPizza);
		
		return "redirect:/pizze";
	}
	
	//DELETE
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes)
	{
		pizzaService.deleteById(id);
		
		attributes.addFlashAttribute("successMessage", "Pizza con id " + id + " è stata eliminata");
		
		return "redirect:/pizze";
	}
	
	

}
