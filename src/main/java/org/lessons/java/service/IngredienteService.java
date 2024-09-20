package org.lessons.java.service;

import java.util.List;

import org.lessons.java.model.Ingrediente;
import org.lessons.java.repo.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {
	
	@Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }
    
    public List<Ingrediente> findAllById(List<Integer> id) {
    	return ingredienteRepository.findAllById(id);
    }

    public Ingrediente save(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public void deleteById(Integer id) {
    	ingredienteRepository.deleteById(id);
    }

    public Ingrediente findById(Integer id) {
        return ingredienteRepository.findById(id).orElse(null);
    }

}
