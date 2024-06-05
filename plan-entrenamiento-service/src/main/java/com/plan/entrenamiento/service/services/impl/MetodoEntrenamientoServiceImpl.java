package com.plan.entrenamiento.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plan.entrenamiento.service.models.MetodoEntrenamiento;
import com.plan.entrenamiento.service.persistence.IMetodoDAO;
import com.plan.entrenamiento.service.services.MetodoEntrenamientoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetodoEntrenamientoServiceImpl implements MetodoEntrenamientoService  {

    private final IMetodoDAO iMetodoDAO;

    @Override
    public List<MetodoEntrenamiento> findAll() {
        return this.iMetodoDAO.findAll();
    }

    @Override
    public Optional<MetodoEntrenamiento> findById(Long id) {
        return this.iMetodoDAO.findById(id);
    }

    @Override
    public Optional<MetodoEntrenamiento> findByNombre(String nombre) {
        return this.iMetodoDAO.findByNombre(nombre);
    }

    @Override
    public void save(MetodoEntrenamiento metodo) {
        this.iMetodoDAO.save(metodo);
    }

    @Override
    public void deleteById(Long id) {
        this.iMetodoDAO.deleteById(id);
    }
    
}
