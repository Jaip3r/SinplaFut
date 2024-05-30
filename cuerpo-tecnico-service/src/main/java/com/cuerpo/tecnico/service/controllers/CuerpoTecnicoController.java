package com.cuerpo.tecnico.service.controllers;

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

import com.cuerpo.tecnico.service.controllers.dtos.CuerpoTecnicoDTO;
import com.cuerpo.tecnico.service.controllers.dtos.CuerpoTecnicoResponseDTO;
import com.cuerpo.tecnico.service.controllers.payload.ApiResponse;
import com.cuerpo.tecnico.service.exception.ResourceAlreadyExistsException;
import com.cuerpo.tecnico.service.exception.ResourceNotFoundException;
import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;
import com.cuerpo.tecnico.service.services.CuerpoTecnicoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("staff")
@RequiredArgsConstructor
@Slf4j
public class CuerpoTecnicoController {

    // Services
    private final CuerpoTecnicoService cuerpoTecnicoService;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> getStaff() {
        
        // Obtenemos la lista de todos los integrantes del cuerpo técnico actualmente activos
        List<CuerpoTecnicoResponseDTO> staffList = this.cuerpoTecnicoService.findAll()
                .stream().map(this::staffToResponseDTO).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(staffList)
            .build()
        );

    }

    @GetMapping("/findByTipo/{tipo}")
    public ResponseEntity<ApiResponse> getStaffByTipo(@PathVariable("tipo") String tipo) {

        // Verificación de no toparse con tipos no válidos
        CuerpoTecnicoType tipoEnum;
        try {
            tipoEnum = CuerpoTecnicoType.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                    .flag(false)
                    .code(400)
                    .message("Tipo de staff técnico inválido")
                    .data("no message provided")
                    .build()
            );
        }
        
        // Obtenemos la lista de ciertos integrantes del cuerpo técnico de acuero a su tipo
        List<CuerpoTecnicoResponseDTO> staffTipoList = this.cuerpoTecnicoService.findByTipo(tipoEnum)
                .stream().map(this::staffToResponseDTO).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(staffTipoList)
            .build()
        );

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getStaffById(@PathVariable Long id) {
        
        // Obtenemos el integrante del cuerpo técnico 
        Optional<CuerpoTecnico> staff = this.cuerpoTecnicoService.findById(id);

        // Verificaciones de identidad
        if (staff.isEmpty()){
            log.warn("Staff con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Staff", "identificador", id);
        }

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.staffToResponseDTO(staff.get()))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createStaff(@RequestBody @Valid CuerpoTecnicoDTO cTecnicoDTO){

        // Verificaciones antes de crear el registro
        if (this.cuerpoTecnicoService.findByEmail(cTecnicoDTO.email()).isPresent()){
            log.warn("Intento de registro de correo ya existente: {}", cTecnicoDTO.email());
            throw new ResourceAlreadyExistsException("Staff", "email", cTecnicoDTO.email());
        }

        if (this.cuerpoTecnicoService.findByDocumento(cTecnicoDTO.documento()).isPresent()){
            log.warn("Intento de registro de documento ya existente: {}", cTecnicoDTO.documento());
            throw new ResourceAlreadyExistsException("Staff", "documento", cTecnicoDTO.documento());
        }

        // Creamos el nuevo integrante con la información proporcionada
        CuerpoTecnico cTecnico = CuerpoTecnico
                    .builder()
                    .nombre(cTecnicoDTO.nombre())
                    .apellido(cTecnicoDTO.apellido())
                    .fecha_nacimiento(cTecnicoDTO.fecha_nacimiento())
                    .email(cTecnicoDTO.email())
                    .documento(cTecnicoDTO.documento())
                    .telefono(cTecnicoDTO.telefono())
                    .equipoId(cTecnicoDTO.equipoId())
                    .tipo(CuerpoTecnicoType.valueOf(cTecnicoDTO.tipo().toUpperCase()))
                    .build();
        
        this.cuerpoTecnicoService.save(cTecnico);

        log.info("POST: staff {}", cTecnico);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Nuevo integrante registrado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateStaff(@RequestBody @Valid CuerpoTecnicoDTO cTecnicoDTO, @PathVariable Long id){

        // Obtenemos el miembro del staff técnico
        Optional<CuerpoTecnico> cOptional = this.cuerpoTecnicoService.findById(id);

        // Verificaciones de identidad
        if (cOptional.isEmpty()){
            log.warn("Staff con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Staff", "identificador", id);
        }

        // Verificaciones antes de crear el registro
        if (this.cuerpoTecnicoService.findByEmail(cTecnicoDTO.email()).isPresent() && !cTecnicoDTO.email().equals(cOptional.get().getEmail())){
            log.warn("Intento de registro de correo ya existente: {}", cTecnicoDTO.email());
            throw new ResourceAlreadyExistsException("Staff", "email", cTecnicoDTO.email());
        }

        if (this.cuerpoTecnicoService.findByDocumento(cTecnicoDTO.documento()).isPresent() && !cTecnicoDTO.documento().equals(cOptional.get().getDocumento())){
            log.warn("Intento de registro de documento ya existente: {}", cTecnicoDTO.documento());
            throw new ResourceAlreadyExistsException("Staff", "documento", cTecnicoDTO.documento());
        }

        // Actualizamos la data del integrante
        CuerpoTecnico cTecnico = cOptional.get();

        cTecnico.setNombre(cTecnicoDTO.nombre());
        cTecnico.setApellido(cTecnicoDTO.apellido());
        cTecnico.setFecha_nacimiento(cTecnicoDTO.fecha_nacimiento());
        cTecnico.setEmail(cTecnicoDTO.email());
        cTecnico.setDocumento(cTecnicoDTO.documento());
        cTecnico.setTelefono(cTecnicoDTO.telefono());
        cTecnico.setEquipoId(cTecnicoDTO.equipoId());
        cTecnico.setTipo(CuerpoTecnicoType.valueOf(cTecnicoDTO.tipo().toUpperCase()));
        
        // Guardamos los cambios
        this.cuerpoTecnicoService.save(cTecnico);

        log.info("PUT: staff {}", cTecnico);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Integrante actualizado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/unlink/{id}")
    public ResponseEntity<ApiResponse> unlinkStaff(@PathVariable Long id){

        // Obtenemos el integrante del cuerpo técnico 
        Optional<CuerpoTecnico> staff = this.cuerpoTecnicoService.findById(id);

        // Verificaciones de identidad
        if (staff.isEmpty()){
            log.warn("Staff con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Staff", "identificador", id);
        }

        // Finalizamos la vigencia del contrato del integrante
        CuerpoTecnico cTecnico = staff.get();
        Long equipo = cTecnico.getEquipoId();

        // En caso de intentar finalizar la vigencia de un integrante ya desvinculado
        if (cTecnico.getEquipoId() == null) {
            log.info("Intento de finalizar la vigencia de un integrante sin ninguna afiliación registada: {} {}", cTecnico.getNombre(), cTecnico.getApellido());
            return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("El integrante especificado no se encuentra vinculado a ningún equipo")
                .data("No data provided")
                .build()
            );
        }

        // Desvinculamos al integrante en especifico
        cTecnico.setEquipoId(null);

        // Guardamos los cambios
        this.cuerpoTecnicoService.save(cTecnico);

        log.info("Desvinculación del integrante con identificador {}, del equipo {}", id, equipo);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Desvinculación del integrante realizada correctamente")
            .data("No data provided")
            .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStaff(@PathVariable Long id){ 

        // Obtenemos el integrante del cuerpo técnico 
        Optional<CuerpoTecnico> staff = this.cuerpoTecnicoService.findById(id);
        
        // Verificaciones de identidad
        if (staff.isEmpty()){
            log.warn("Staff con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Staff", "identificador", id);
        }

        // No permitimos la eliminación de un integrante con vinculación vigente
        if (staff.get().getEquipoId() != null){
            log.info("Intento de eliminación de integrante con vinculación vigente con identificador {}", id);
            return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("El integrante aún se encuentra vinculado a un equipo")
                .data("No data provided")
                .build()
            );
        }

        // Eliminamos al integrante del cuerpo técnico en cuestión
        this.cuerpoTecnicoService.deleteById(id);

        log.info("DELETE: Integrante del staff técnico con identificador {}", id);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Integrante del cuerpo técnico eliminado correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Endpoints para ser consumidos por otros MS

    @GetMapping("/findByEquipo/{equipoId}")
    public ResponseEntity<?> findByEquipo(@PathVariable Long equipoId){
        return ResponseEntity.ok(this.cuerpoTecnicoService.findByEquipo(equipoId)
                        .stream()
                        .map(this::staffToResponseDTO).toList());
    }


    // Converter methods

    private CuerpoTecnicoResponseDTO staffToResponseDTO(CuerpoTecnico staff) {

        return CuerpoTecnicoResponseDTO.builder()
                .id(staff.getId())
                .nombre(staff.getNombre())
                .apellido(staff.getApellido())
                .fecha_nacimiento(staff.getFecha_nacimiento().toString())
                .email(staff.getEmail())
                .documento(staff.getDocumento())
                .telefono(staff.getTelefono())
                .tipo(staff.getTipo().toString().toLowerCase())
                .equipo_id(staff.getEquipoId())
                .build();

    }
    
}
