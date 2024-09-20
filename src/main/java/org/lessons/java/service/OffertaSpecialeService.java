package org.lessons.java.service;

import org.lessons.java.model.OffertaSpeciale;
import org.lessons.java.repo.OffertaSpecialeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffertaSpecialeService {

    @Autowired
    private OffertaSpecialeRepository offertaSpecialeRepository;

    public List<OffertaSpeciale> findAll() {
        return offertaSpecialeRepository.findAll();
    }

    public OffertaSpeciale save(OffertaSpeciale offertaSpeciale) {
        return offertaSpecialeRepository.save(offertaSpeciale);
    }

    public void deleteById(Integer id) {
        offertaSpecialeRepository.deleteById(id);
    }

    public OffertaSpeciale findById(Integer id) {
        return offertaSpecialeRepository.findById(id).orElse(null);
    }
}
