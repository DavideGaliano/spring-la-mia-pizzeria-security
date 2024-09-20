package org.lessons.java.repo;

import java.util.List;

import org.lessons.java.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
	
	List<Pizza> findByNameContainingIgnoreCase(String name);
}
