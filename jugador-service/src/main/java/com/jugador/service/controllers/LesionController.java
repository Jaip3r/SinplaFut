package com.jugador.service.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jugador.service.controllers.dtos.LesionDTO;
import com.jugador.service.controllers.dtos.LesionResponseDTO;
import com.jugador.service.controllers.payload.ApiResponse;
import com.jugador.service.exception.ResourceAlreadyExistsException;
import com.jugador.service.exception.ResourceNotFoundException;
import com.jugador.service.models.Lesion;
import com.jugador.service.services.LesionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("lesion")
@RequiredArgsConstructor
@Slf4j
public class LesionController {

    // Services
    private final LesionService lService;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> getLesiones() {
        
        // Obtenemos la lista de todos los integrantes del cuerpo técnico actualmente activos
        List<LesionResponseDTO> lesionList = this.lService.findAll()
                .stream().map(this::lesionToResponseDTO).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(lesionList)
            .build()
        );

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getLesionById(@PathVariable Long id) {

        // Obtenemos la lesion
        Optional<Lesion> optional = this.lService.findById(id);

        // Verificaciones de identidad
        if (optional.isEmpty()){
            log.warn("Lesion con identificador {} no identificada", id);
            throw new ResourceNotFoundException("Lesión", "identificador", id);
        }

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.lesionToResponseDTO(optional.get()))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createLesion(@RequestBody @Valid LesionDTO lDto) {

        // Verificaciones antes de crear el registro
        if (this.lService.findByNombre(lDto.nombre()).isPresent()){
            log.warn("Intento de registro de lesion ya existente: {}", lDto.nombre());
            throw new ResourceAlreadyExistsException("Lesión", "nombre", lDto.nombre());
        }

        // Creamos la lesión con la información proporcionada
        Lesion lesion = Lesion
                .builder()
                .nombre(lDto.nombre())
                .tratamiento(lDto.tratamiento())
                .observaciones(lDto.observaciones())
                .build();
        
        this.lService.save(lesion);

        log.info("POST: Lesion {}", lesion);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Nueva lesión registrada correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@RequestBody LesionDTO lDto, @PathVariable Long id){

        // Obtenemos la lesion
        Optional<Lesion> optional = this.lService.findById(id);

        // Verificaciones de identidad
        if (optional.isEmpty()){
            log.warn("Lesion con identificador {} no identificada", id);
            throw new ResourceNotFoundException("Lesión", "identificador", id);
        }

        if (this.lService.findByNombre(lDto.nombre()).isPresent() && !optional.get().getNombre().equals(lDto.nombre())){
            log.warn("Intento de registro de lesion ya existente: {}", lDto.nombre());
            throw new ResourceAlreadyExistsException("Lesión", "nombre", lDto.nombre());
        }

        // Actualizamos la data de la lesión
        Lesion lesion = optional.get();

        lesion.setNombre(lDto.nombre());
        lesion.setTratamiento(lDto.tratamiento());
        lesion.setObservaciones(lDto.observaciones());
        
        // Guardamos los cambios
        this.lService.save(lesion);

        log.info("PUT: Lesion {}", lesion);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Lesión actualizada correctamente")
            .data("No data provided")
            .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLesion(@PathVariable Long id) {

        // Obtenemos la lesion
        Optional<Lesion> optional = this.lService.findById(id);

        // Verificaciones de identidad
        if (optional.isEmpty()){
            log.warn("Lesion con identificador {} no identificada", id);
            throw new ResourceNotFoundException("Lesión", "identificador", id);
        }

        // Eliminamos la lesión en cuestión
        this.lService.deleteById(id);

        log.info("DELETE: Lesión con identificador {}", id);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Lesión eliminada correctamente")
            .data("No data provided")
            .build()
        );

    }

    // Converter methods

    private LesionResponseDTO lesionToResponseDTO(Lesion lesion) {

        return LesionResponseDTO.builder()
                .id(lesion.getId())
                .nombre(lesion.getNombre())
                .tratamiento(lesion.getTratamiento())
                .observaciones(lesion.getObservaciones())
                .build();

    }
    
}
