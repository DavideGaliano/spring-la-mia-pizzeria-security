package org.lessons.java.repo;

import org.lessons.java.model.Ordine;
import org.lessons.java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    List<Ordine> findByUtente(User utente);
}
