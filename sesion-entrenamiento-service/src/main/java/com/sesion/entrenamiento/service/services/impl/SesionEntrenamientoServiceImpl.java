package com.sesion.entrenamiento.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sesion.entrenamiento.service.models.SesionEntrenamiento;
import com.sesion.entrenamiento.service.persistence.ISesionDAO;
import com.sesion.entrenamiento.service.services.SesionEntrenamientoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SesionEntrenamientoServiceImpl implements SesionEntrenamientoService {

    private final ISesionDAO iSesionDAO;
    
    @Override
    public List<SesionEntrenamiento> findAll() {
        return this.iSesionDAO.findAll();
    }

    @Override
    public List<SesionEntrenamiento> findByEquipo(Long equipo_id) {
        return this.iSesionDAO.findByEquipo(equipo_id);
    }

    @Override
    public Optional<SesionEntrenamiento> findById(Long id) {
        return this.iSesionDAO.findById(id);
    }

    @Override
    public void save(SesionEntrenamiento sesion) {
        this.iSesionDAO.save(sesion);
    }

    @Override
    public void deleteById(Long id) {
        this.iSesionDAO.deleteById(id);
    }


    
}
