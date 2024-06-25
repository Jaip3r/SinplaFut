package com.sesion.entrenamiento.service.controllers;

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

import com.sesion.entrenamiento.service.controllers.dtos.MetodoEntrenamientoDTO;
import com.sesion.entrenamiento.service.controllers.dtos.MetodoEntrenamientoResponseDTO;
import com.sesion.entrenamiento.service.controllers.payload.ApiResponse;
import com.sesion.entrenamiento.service.exception.ResourceAlreadyExistsException;
import com.sesion.entrenamiento.service.exception.ResourceNotFoundException;
import com.sesion.entrenamiento.service.models.MetodoEntrenamiento;
import com.sesion.entrenamiento.service.models.TipoCarga;
import com.sesion.entrenamiento.service.models.TipoIntensidad;
import com.sesion.entrenamiento.service.services.MetodoEntrenamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("metodo")
@RequiredArgsConstructor
@Slf4j
public class MetodoEntrenamientoController {

    //services
    private final MetodoEntrenamientoService mService;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> getMetodos() {
        
        // Obtenemos la lista de todas los metodos de entrenamiento
        List<MetodoEntrenamientoResponseDTO> methodList = this.mService.findAll()
                .stream().map(this::methodToResponseDTO).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(methodList)
            .build()
        );

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getMethodById(@PathVariable Long id) {
        
        // Obtenemos el método de entrenamiento
        Optional<MetodoEntrenamiento> metodo = this.mService.findById(id);

        // Verificaciones de identidad
        if (metodo.isEmpty()){
            log.warn("Metodo con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Método", "identificador", id);
        }

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.methodToResponseDTO(metodo.get()))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMethod(@RequestBody @Valid MetodoEntrenamientoDTO mDto){

        // Verificaciones antes de crear el registro
        if (this.mService.findByNombre(mDto.nombre()).isPresent()){
            log.warn("Intento de registro de método ya existente: {}", mDto.nombre());
            throw new ResourceAlreadyExistsException("Método", "nombre", mDto.nombre());
        }

        // Creamos el nuevo método de entrenamiento con la información proporcionada
        MetodoEntrenamiento mEntrenamiento = MetodoEntrenamiento
                    .builder()
                    .nombre(mDto.nombre())
                    .descripcion(mDto.descripcion())
                    .carga(TipoCarga.valueOf(mDto.carga().toUpperCase()))
                    .intensidad(TipoIntensidad.valueOf(mDto.intensidad().toUpperCase()))
                    .duracion(mDto.duracion())
                    .build();
        
        this.mService.save(mEntrenamiento);

        log.info("POST: method {}", mEntrenamiento);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Nuevo método de entrenamiento registrado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMethod(@RequestBody @Valid MetodoEntrenamientoDTO mDto, @PathVariable Long id){

        // Obtenemos el miembro del staff técnico
        Optional<MetodoEntrenamiento> mOptional = this.mService.findById(id);

        // Verificaciones de identidad
        if (mOptional.isEmpty()){
            log.warn("Metodo con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Método", "identificador", id);
        }

        // Verificaciones antes de crear el registro
        if (this.mService.findByNombre(mDto.nombre()).isPresent() && !mDto.nombre().equals(mOptional.get().getNombre())){
            log.warn("Intento de registro de método ya existente: {}", mDto.nombre());
            throw new ResourceAlreadyExistsException("Método", "nombre", mDto.nombre());
        }

        // Actualizamos la data del método
        MetodoEntrenamiento mEntrenamiento = mOptional.get();

        mEntrenamiento.setNombre(mDto.nombre());
        mEntrenamiento.setDescripcion(mDto.descripcion());
        mEntrenamiento.setCarga(TipoCarga.valueOf(mDto.carga().toUpperCase()));
        mEntrenamiento.setIntensidad(TipoIntensidad.valueOf(mDto.intensidad().toUpperCase()));
        
        // Guardamos los cambios
        this.mService.save(mEntrenamiento);

        log.info("PUT: method {}", mEntrenamiento);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Método de entrenamiento actualizado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMethod(@PathVariable Long id){

        // Obtenemos el miembro del staff técnico
        Optional<MetodoEntrenamiento> mOptional = this.mService.findById(id);

        // Verificaciones de identidad
        if (mOptional.isEmpty()){
            log.warn("Metodo con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Método", "identificador", id);
        }

        // Eliminamos el método de entrenamiento
        this.mService.deleteById(id);

        log.info("DELETE: Método de entrenamiento con identificador {}", id);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Método de entrenamiento eliminado correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Converter methods

    private MetodoEntrenamientoResponseDTO methodToResponseDTO(MetodoEntrenamiento metodo) {

        return MetodoEntrenamientoResponseDTO.builder()
                .id(metodo.getId())
                .nombre(metodo.getNombre())
                .descripcion(metodo.getDescripcion())
                .carga(metodo.getCarga().toString().toLowerCase())
                .intensidad(metodo.getIntensidad().toString().toLowerCase())
                .duracion(metodo.getDuracion())
                .build();

    }
    
}
