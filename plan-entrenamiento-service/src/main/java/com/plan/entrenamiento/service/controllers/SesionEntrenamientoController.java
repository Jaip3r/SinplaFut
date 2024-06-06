package com.plan.entrenamiento.service.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plan.entrenamiento.service.controllers.dtos.SesionEntrenamientoDTO;
import com.plan.entrenamiento.service.controllers.dtos.SesionEntrenamientoResponseDTO;
import com.plan.entrenamiento.service.controllers.payload.ApiResponse;
import com.plan.entrenamiento.service.exception.ResourceNotFoundException;
import com.plan.entrenamiento.service.models.SesionEntrenamiento;
import com.plan.entrenamiento.service.models.TipoSesionEntrenamiento;
import com.plan.entrenamiento.service.services.SesionEntrenamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("sesion")
@RequiredArgsConstructor
@Slf4j
public class SesionEntrenamientoController {

    // services
    private final SesionEntrenamientoService service;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> getSesiones() {
        
        // Obtenemos la lista de todas las sesiones de entrenamiento
        List<SesionEntrenamientoResponseDTO> sesionList = this.service.findAll()
                .stream().map(this::sesionToResponseDTO).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(sesionList)
            .build()
        );

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getSesionById(@PathVariable Long id) {
        
        // Obtenemos la sesión de entrenamiento 
        Optional<SesionEntrenamiento> sesion = this.service.findById(id);

        // Verificaciones de identidad
        if (sesion.isEmpty()){
            log.warn("Sesion con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Sesion", "identificador", id);
        }

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.sesionToResponseDTO(sesion.get()))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createSesion(@RequestBody @Valid SesionEntrenamientoDTO sDto){

        // Creamos la nueva sesión de entrenamiento con la información proporcionada
        SesionEntrenamiento sEntrenamiento = SesionEntrenamiento
                    .builder()
                    .nombre(sDto.nombre())
                    .descripcion(sDto.descripcion())
                    .fecha_inicio(sDto.fecha_inicio())
                    .hora(sDto.hora())
                    .duracion(sDto.duracion())
                    .tipo_sesion(TipoSesionEntrenamiento.valueOf(sDto.tipo().toUpperCase()))
                    .build();
        
        this.service.save(sEntrenamiento);

        log.info("POST: sesion {}", sEntrenamiento);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Nueva sesión registrada correctamente")
            .data("No data provided")
            .build()
        );

    }

    // Converter methods

    private SesionEntrenamientoResponseDTO sesionToResponseDTO(SesionEntrenamiento sesion) {

        return SesionEntrenamientoResponseDTO.builder()
                .id(sesion.getId())
                .nombre(sesion.getNombre())
                .descripcion(sesion.getDescripcion())
                .fecha_inicio(sesion.getFecha_inicio().toString())
                .hora(sesion.getHora())
                .duracion(sesion.getDuracion())
                .tipo_sesion(sesion.getTipo_sesion().toString().toLowerCase())
                .equipoId(sesion.getEquipoId())
                .build();

    }
    
}
