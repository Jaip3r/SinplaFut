package com.cuerpo.tecnico.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;
import com.cuerpo.tecnico.service.persistence.ICuerpoTecnicoDAO;
import com.cuerpo.tecnico.service.services.CuerpoTecnicoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuerpoTecnicoServiceImpl implements CuerpoTecnicoService {

    private final ICuerpoTecnicoDAO iCuerpoTecnicoDAO;

    @Override
    public List<CuerpoTecnico> findAll() {
        return this.iCuerpoTecnicoDAO.findAll();
    }

    @Override
    public List<CuerpoTecnico> findByTipo(CuerpoTecnicoType tipo, Long equipoId) {
        return this.iCuerpoTecnicoDAO.findByTipo(tipo, equipoId);
    }

    @Override
    public List<CuerpoTecnico> findByEquipo(Long equipo_id) {
        return this.iCuerpoTecnicoDAO.findByEquipo(equipo_id);
    }

    @Override
    public List<CuerpoTecnico> findAllByEmailOrDocumento(String email, String documento) {
        return this.iCuerpoTecnicoDAO.findAllByEmailOrDocumento(email, documento);
    }

    @Override
    public Optional<CuerpoTecnico> findByEmailOrDocumento(String email, String documento) {
        return this.iCuerpoTecnicoDAO.findByEmailOrDocumento(email, documento);
    }

    @Override
    public Optional<CuerpoTecnico> findById(Long id) {
        return this.iCuerpoTecnicoDAO.findById(id);
    }
    
    @Override
    public void save(CuerpoTecnico cuerpoTecnico) {
        this.iCuerpoTecnicoDAO.save(cuerpoTecnico);
    }

    @Override
    public void deleteById(Long id) {
        this.iCuerpoTecnicoDAO.deleteById(id);
    }

}
