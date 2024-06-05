package com.plan.entrenamiento.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.plan.entrenamiento.service.models.SesionEntrenamiento;
import com.plan.entrenamiento.service.persistence.ISesionDAO;
import com.plan.entrenamiento.service.persistence.repositories.SesionEntrenamientoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ISesionDAOImpl implements ISesionDAO {

    private final SesionEntrenamientoRepository sRepository;

    @Override
    public List<SesionEntrenamiento> findAll() {
        return this.sRepository.findAll();
    }

    @Override
    public List<SesionEntrenamiento> findByEquipo(Long equipo_id) {
        return this.sRepository.findByEquipoId(equipo_id);
    }

    @Override
    public Optional<SesionEntrenamiento> findById(Long id) {
        return this.sRepository.findById(id);
    }

    @Override
    public void save(SesionEntrenamiento sesion) {
        this.sRepository.save(sesion);
    }

    @Override
    public void deleteById(Long id) {
        this.sRepository.deleteById(id);
    }
    
}
