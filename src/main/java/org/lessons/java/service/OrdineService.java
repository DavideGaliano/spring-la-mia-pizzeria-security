package org.lessons.java.service;

import org.lessons.java.model.Ordine;
import org.lessons.java.model.User;
import org.lessons.java.repo.OrdineRepository;
import org.lessons.java.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Ordine> getOrdiniByUser(User user) {
        return ordineRepository.findByUtente(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
