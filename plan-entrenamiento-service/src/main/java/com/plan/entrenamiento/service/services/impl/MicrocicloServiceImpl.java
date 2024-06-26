package com.plan.entrenamiento.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plan.entrenamiento.service.models.Microciclo;
import com.plan.entrenamiento.service.persistence.IMicrocicloDAO;
import com.plan.entrenamiento.service.services.MicrocicloService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MicrocicloServiceImpl implements MicrocicloService {
    
    private final IMicrocicloDAO iMicrocicloDAO;

    @Override
    public List<Microciclo> findAll() {
        return this.iMicrocicloDAO.findAll();
    }

    @Override
    public Optional<Microciclo> findByNombre(String nombre) {
        return this.iMicrocicloDAO.findByNombre(nombre);
    }

    @Override
    public Optional<Microciclo> findById(Long id) {
        return this.iMicrocicloDAO.findById(id);
    }

    @Override
    public void save(Microciclo microciclo) {
        this.iMicrocicloDAO.save(microciclo);
    }

    @Override
    public void deleteById(Long id) {
        this.iMicrocicloDAO.deleteById(id);
    }



}
