package org.lessons.java.repo;

import org.lessons.java.model.Carrello;
import org.lessons.java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {
    Carrello findByUtente(User utente);
}
