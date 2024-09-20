package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.Ingrediente;
import org.lessons.java.model.Pizza;
import org.lessons.java.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/ingredienti")
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@GetMapping
    public String listIngredienti(Model model) {
        List<Ingrediente> ingredienti = ingredienteService.findAll();
        model.addAttribute("ingredienti", ingredienti);
        return "ingredienti/index";
    }
	
	@GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "ingredienti/create";
    }
	
	@PostMapping("/create")
    public String createIngrediente(Ingrediente ingrediente) {
		ingredienteService.save(ingrediente);
        return "redirect:/ingredienti";
    }
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("ingrediente", ingredienteService.findById(id));
		
		return "ingredienti/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente ,BindingResult bindingResult, Model model)
	{
		if(bindingResult.hasErrors()) {
			return "/ingredienti/edit";
		}
		ingredienteService.save(formIngrediente);
		
		return "redirect:/ingredienti";
	}
	
	@PostMapping("/delete/{id}")
    public String deleteIngrediente(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		ingredienteService.deleteById(id);
		attributes.addFlashAttribute("successMessage", "Ingrediente con id " + id + " è stato eliminato");
		
        return "redirect:/ingredienti";
    }

}
