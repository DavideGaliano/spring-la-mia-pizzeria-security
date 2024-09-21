package org.lessons.java.controller;

import java.util.List;
import java.util.Set;

import org.lessons.java.model.Role;
import org.lessons.java.model.User;
import org.lessons.java.repo.RoleRepository;
import org.lessons.java.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Mostra il form di creazione di un nuovo utente
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User()); 
        model.addAttribute("userTypes", List.of("Private", "Company"));
        return "users/create";
    }

    // Salva il nuovo utente
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @ModelAttribute("userType") String userType,  // Aggiungiamo il tipo utente selezionato
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userTypes", List.of("Private", "Company"));
            return "users/create";  // Ritorna al form in caso di errore
        }
        
     // Codifica la password usando BCrypt
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
     // Imposta il tipo di utente selezionato
        user.setUserType(userType);
        
     // Recupera il ruolo predefinito per l'utente
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(userRole));

     // Aggiungi campi extra in base al tipo di utente
        if (userType.equals("Private")) {
            user.setFirstName(user.getFirstName());
            user.setLastName(user.getLastName());
            user.setCompanyName(null);  // Puliamo i campi aziendali
            user.setPiva(null);
        } else if (userType.equals("Company")) {
            user.setCompanyName(user.getCompanyName());
            user.setPiva(user.getPiva());
            user.setFirstName(null);  // Puliamo i campi privati
            user.setLastName(null);
        }
        
     // Salva l'utente nel database
        userRepository.save(user);

        return "redirect:/pizze/index-order";  // Reindirizza alle pizze
    }
}
