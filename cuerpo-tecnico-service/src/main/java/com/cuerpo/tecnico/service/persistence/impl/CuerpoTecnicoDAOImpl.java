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
    public List<CuerpoTecnico> findByTipo(CuerpoTecnicoType tipo) {
        return this.cTecnicoRepository.findAllByTipo(tipo);
    }

    @Override
    public List<CuerpoTecnico> findByEquipo(Long equipo_id) {
        return this.cTecnicoRepository.findAllByEquipoId(equipo_id);
    }
    
    @Override
    public Optional<CuerpoTecnico> findById(Long id) {
        return this.cTecnicoRepository.findById(id);
    }

    @Override
    public Optional<CuerpoTecnico> findByDocumento(String documento) {
        return Optional.ofNullable(this.cTecnicoRepository.findByDocumento(documento));
    }

    @Override
    public Optional<CuerpoTecnico> findByEmail(String email) {
        return Optional.ofNullable(this.cTecnicoRepository.findByEmail(email));
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
