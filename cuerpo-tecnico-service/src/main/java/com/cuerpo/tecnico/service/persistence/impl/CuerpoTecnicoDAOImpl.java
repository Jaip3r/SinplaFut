package com.cuerpo.tecnico.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;
import com.cuerpo.tecnico.service.persistence.ICuerpoTecnicoDAO;
import com.cuerpo.tecnico.service.persistence.repositories.CuerpoTecnicoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CuerpoTecnicoDAOImpl implements ICuerpoTecnicoDAO {

    private final CuerpoTecnicoRepository cTecnicoRepository;

    @Override
    public List<CuerpoTecnico> findAll() {
        return this.cTecnicoRepository.findAll();
    }

    @Override
    public List<CuerpoTecnico> findByTipo(CuerpoTecnicoType tipo, Long equipoId) {
        return this.cTecnicoRepository.findAllByTipoAndEquipoId(tipo, equipoId);
    }

    @Override
    public List<CuerpoTecnico> findByEquipo(Long equipo_id) {
        return this.cTecnicoRepository.findAllByEquipoId(equipo_id);
    }

    @Override
    public List<CuerpoTecnico> findAllByEmailOrDocumento(String email, String documento) {
        return this.cTecnicoRepository.findAllByEmailOrDocumento(email, documento);
    }

    @Override
    public Optional<CuerpoTecnico> findByEmailOrDocumento(String email, String documento) {
        return this.cTecnicoRepository.findFirstByEmailOrDocumento(email, documento);
    }
    
    @Override
    public Optional<CuerpoTecnico> findById(Long id) {
        return this.cTecnicoRepository.findById(id);
    }

    @Override
    public void save(CuerpoTecnico cuerpoTecnico) {
        this.cTecnicoRepository.save(cuerpoTecnico);
    }

    @Override
    public void deleteById(Long id) {
        this.cTecnicoRepository.deleteById(id);
    }

}
