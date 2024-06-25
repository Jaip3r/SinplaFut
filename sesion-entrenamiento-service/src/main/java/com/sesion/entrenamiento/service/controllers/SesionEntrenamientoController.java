package com.sesion.entrenamiento.service.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sesion.entrenamiento.service.controllers.dtos.MetodoEntrenamientoResponseDTO;
import com.sesion.entrenamiento.service.controllers.dtos.SesionEntrenamientoDTO;
import com.sesion.entrenamiento.service.controllers.dtos.SesionEntrenamientoResponseDTO;
import com.sesion.entrenamiento.service.controllers.payload.ApiResponse;
import com.sesion.entrenamiento.service.exception.ResourceNotFoundException;
import com.sesion.entrenamiento.service.models.MetodoEntrenamiento;
import com.sesion.entrenamiento.service.models.SesionEntrenamiento;
import com.sesion.entrenamiento.service.models.TipoCarga;
import com.sesion.entrenamiento.service.models.TipoIntensidad;
import com.sesion.entrenamiento.service.models.TipoSesionEntrenamiento;
import com.sesion.entrenamiento.service.services.SesionEntrenamientoService;

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

        // Convertimos los metodos a la entidad correspondiente
        List<MetodoEntrenamiento> mList = sDto.metodos()
                .stream().map(this::responseToMetodoEntrenamiento).toList();

        // Verificamos que la duracion de los metodo no supere la de la sesión
        int duracionTotalMetodos = mList.stream()
                                    .mapToInt(MetodoEntrenamiento::getDuracion)
                                    .sum();

        if (duracionTotalMetodos > sDto.duracion()){

            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("La duración total de los métodos de entrenamiento supera la de la sesión")
                .data("Error de validación")
                .build()
            );

        }

        // Creamos la nueva sesión de entrenamiento con la información proporcionada
        SesionEntrenamiento sEntrenamiento = SesionEntrenamiento
                    .builder()
                    .nombre(sDto.nombre())
                    .descripcion(sDto.descripcion())
                    .fecha_inicio(sDto.fecha_inicio())
                    .hora(sDto.hora())
                    .duracion(sDto.duracion())
                    .tipo_sesion(TipoSesionEntrenamiento.valueOf(sDto.tipo().toUpperCase()))
                    .equipoId(sDto.equipoId())
                    .metodos(mList)
                    .build();
        
        // Registramos los cambios
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSesion(@PathVariable Long id){

        // Obtenemos la sesión de entrenamiento 
        Optional<SesionEntrenamiento> sesion = this.service.findById(id);

        // Verificaciones de identidad
        if (sesion.isEmpty()){
            log.warn("Sesion con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Sesion", "identificador", id);
        }

        // Eliminamos la sesión de entrenamiento
        this.service.deleteById(id);

        log.info("DELETE: Sesion de entrenamiento con identificador {}", id);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Sesión de entrenamiento eliminada correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Endpoints para ser consumidos por otros MS

    @GetMapping("/findByEquipo/{equipoId}")
    public ResponseEntity<?> findByEquipo(@PathVariable Long equipoId){
        return ResponseEntity.ok(this.service.findByEquipo(equipoId)
                        .stream()
                        .map(this::sesionToResponseDTO).toList());
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
                .metodos(sesion.getMetodos())
                .build();

    }

    private MetodoEntrenamiento responseToMetodoEntrenamiento(MetodoEntrenamientoResponseDTO metodo){

        return MetodoEntrenamiento.builder()
                .id(metodo.getId())
                .nombre(metodo.getNombre())
                .descripcion(metodo.getDescripcion())
                .carga(TipoCarga.valueOf(metodo.getCarga().toUpperCase()))
                .intensidad(TipoIntensidad.valueOf(metodo.getIntensidad().toUpperCase()))
                .duracion(metodo.getDuracion())
                .build();

    }
    
}
