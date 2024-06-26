package com.plan.entrenamiento.service.controllers;

import java.time.LocalDate;
//import java.util.List;
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

import com.plan.entrenamiento.service.controllers.dtos.PlanEntrenamientoDTO;
import com.plan.entrenamiento.service.controllers.dtos.PlanEntrenamientoResponseDTO;
import com.plan.entrenamiento.service.controllers.payload.ApiResponse;
import com.plan.entrenamiento.service.exception.ResourceAlreadyExistsException;
import com.plan.entrenamiento.service.exception.ResourceNotFoundException;
import com.plan.entrenamiento.service.models.PlanEntrenamiento;
import com.plan.entrenamiento.service.services.PlanEntrenamientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("plan-entrenamiento")
@RequiredArgsConstructor
@Slf4j
public class PlanEntrenamientoController {

    // Services
    private final PlanEntrenamientoService pService;

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getPlanById(@PathVariable Long id) {

        // Obtenemos el plan de entrenamiento
        Optional<PlanEntrenamiento> pOptional = this.pService.findById(id);

        // Verificaciones de identidad
        if (pOptional.isEmpty()){
            log.warn("Plan de entrenamiento con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Plan de entrenamiento", "identificador", id);
        }

        return ResponseEntity.ok(this.createApiResponse(true, 200, "Info obtenida correctamente", this.planToResponseDTO(pOptional.get())));

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPlan(@RequestBody @Valid PlanEntrenamientoDTO pDto) {

        // Verificaciones antes de crear el registro
        if (this.pService.findByNombre(pDto.nombre()).isPresent()){
            log.warn("Intento de un plan de entrenamiento ya existente: {}", pDto.nombre());
            throw new ResourceAlreadyExistsException("Plan de entrenamiento", "nombre", pDto.nombre());
        }

        // Verificación de fechas
        LocalDate fechaInicio = pDto.fechaInicio();
        LocalDate fechaFin = pDto.fechaFin();
        LocalDate now = LocalDate.now();

        if (fechaInicio.isEqual(fechaFin)) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y finalización del plan no pueden coincidir", "No data provided"));
        }

        if (fechaInicio.isBefore(now) || fechaFin.isBefore(now) || fechaInicio.isAfter(fechaFin)) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y finalización del plan no son válidas", "No data provided"));
        }

        // Creamos el nuevo plan de entrenamiento con la información proporcionada
        PlanEntrenamiento pEntrenamiento = PlanEntrenamiento
                            .builder()
                            .nombre(pDto.nombre())
                            .fechaInicio(pDto.fechaInicio())
                            .fechaFin(pDto.fechaFin())
                            .descripcion(pDto.descripcion())
                            .observaciones(pDto.observaciones())
                            .equipoId(pDto.equipoId())
                            .build();
        
        this.pService.save(pEntrenamiento);

        log.info("POST: plan entrenamiento {}", pEntrenamiento);
        return ResponseEntity.ok(this.createApiResponse(true, 200, "Nuevo plan de entrenamiento registrado correctamente", "No data provided"));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updatePlan(@RequestBody @Valid PlanEntrenamientoDTO pDTO, @PathVariable Long id) {

        // Obtenemos el plan de entrenamiento
        Optional<PlanEntrenamiento> pOptional = this.pService.findById(id);

        // Verificaciones de identidad
        if (pOptional.isEmpty()){
            log.warn("Plan de entrenamiento con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Plan de entrenamiento", "identificador", id);
        }

        if (this.pService.findByNombre(pDTO.nombre()).isPresent() && !pOptional.get().getNombre().equals(pDTO.nombre())){
            log.warn("Intento de un plan de entrenamiento ya existente: {}", pDTO.nombre());
            throw new ResourceAlreadyExistsException("Plan de entrenamiento", "nombre", pDTO.nombre());
        }

        // Verificaciones de fechas
        if (pDTO.fechaInicio().isEqual(pDTO.fechaFin())) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y finalización del plan no pueden coincidir", "No data provided"));
        }

        if (pDTO.fechaInicio().isAfter(pDTO.fechaFin())) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y finalización del plan no son válidas", "No data provided"));
        }

        // Actualizamos la data del plan de entrenamiento
        PlanEntrenamiento pEntrenamiento = pOptional.get();

        pEntrenamiento.setNombre(pDTO.nombre());
        pEntrenamiento.setFechaInicio(pDTO.fechaInicio());
        pEntrenamiento.setFechaFin(pDTO.fechaFin());
        pEntrenamiento.setDescripcion(pDTO.descripcion());
        pEntrenamiento.setObservaciones(pDTO.observaciones());

        // Registramos los cambios
        this.pService.save(pEntrenamiento);

        log.info("PUT: plan entrenamiento {}", pEntrenamiento);;
        return ResponseEntity.ok(this.createApiResponse(true, 200, "Plan de entrenamiento actualizado correctamente", "No data providad"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePlan(@PathVariable Long id) {

        // Obtenemos el plan de entrenamiento
        Optional<PlanEntrenamiento> pOptional = this.pService.findById(id);

        // Verificaciones de identidad
        if (pOptional.isEmpty()){
            log.warn("Plan de entrenamiento con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Plan de entrenamiento", "identificador", id);
        }

        // En caso de tener mesociclos activos asociados
        this.pService.deleteById(id);

        log.info("DELETE: plan entrenamiento {}", id);
        return ResponseEntity.ok(this.createApiResponse(true, 200, "Plan de entrenamiento eliminado correctamente", "No data provided"));

    }


    // Converter methods

    private PlanEntrenamientoResponseDTO planToResponseDTO(PlanEntrenamiento plan) {

        return PlanEntrenamientoResponseDTO.builder()
                .id(plan.getId())
                .nombre(plan.getNombre())
                .fechaInicio(plan.getFechaInicio().toString())
                .fechaFin(plan.getFechaFin().toString())
                .descripcion(plan.getDescripcion())
                .observaciones(plan.getObservaciones())
                .equipoId(plan.getEquipoId())
                .build();

    }


    // Aux methods

    private ApiResponse createApiResponse(boolean flag, int code, String message, Object data){
        return ApiResponse.builder()
                .flag(flag)
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
    
}
