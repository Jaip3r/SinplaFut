package com.plan.entrenamiento.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import com.plan.entrenamiento.service.models.Mesociclo;
import com.plan.entrenamiento.service.persistence.IMesocicloDAO;
import com.plan.entrenamiento.service.services.MesociloService;

@Service
@RequiredArgsConstructor
public class MesocicloServiceImpl implements MesociloService {

    private final IMesocicloDAO iMesocicloDAO;

    @Override
    public List<Mesociclo> findAll() {
        return this.iMesocicloDAO.findAll();
    }

    @Override
    public Optional<Mesociclo> findById(Long id) {
        return this.iMesocicloDAO.findById(id);
    }

    @Override
    public void save(Mesociclo mesociclo) {
        this.iMesocicloDAO.save(mesociclo);
    }

    @Override
    public void deleteById(Long id) {
        this.iMesocicloDAO.deleteById(id);
    }
    
}
