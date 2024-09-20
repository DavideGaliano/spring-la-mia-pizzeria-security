package org.lessons.java.controller;

import java.util.List;

import org.lessons.java.model.CompanyUser;
import org.lessons.java.model.PrivateUser;
import org.lessons.java.model.User;
import org.lessons.java.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Mostra il form di creazione di un nuovo utente
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new PrivateUser());  // Predefinito su PrivateUser
        model.addAttribute("userTypes", List.of("Private", "Company"));
        return "users/create";
    }

    // Salva il nuovo utente
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") PrivateUser user,
                             BindingResult bindingResult,
                             @ModelAttribute("userType") String userType,  // Aggiungiamo il tipo utente selezionato
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userTypes", List.of("Private", "Company"));
            return "users/create";  // Ritorna al form in caso di errore
        }

        if (userType.equals("Private")) {
            // Se è un PrivateUser, salviamo l'utente privato
            PrivateUser privateUser = new PrivateUser();
            privateUser.setUsername(user.getUsername());
            privateUser.setEmail(user.getEmail());
            privateUser.setFirstName(user.getFirstName());
            privateUser.setLastName(user.getLastName());
            userRepository.save(privateUser);
        } else if (userType.equals("Company")) {
            // Se è un CompanyUser, creiamo un oggetto CompanyUser
            CompanyUser companyUser = new CompanyUser();
            companyUser.setUsername(user.getUsername());
            companyUser.setEmail(user.getEmail());
            companyUser.setCompanyName(user.getFirstName());  // Simile a nome utente
            companyUser.setPiva(user.getLastName());  // Qui simuliamo la partita IVA
            userRepository.save(companyUser);
        }

        return "redirect:/pizze/index-order";  // Reindirizza alle pizze
    }
}
