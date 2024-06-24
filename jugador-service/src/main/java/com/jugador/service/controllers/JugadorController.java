package com.jugador.service.controllers;

import java.time.LocalDate;
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

import com.jugador.service.controllers.dtos.JugadorDTO;
import com.jugador.service.controllers.dtos.JugadorLesionDTO;
import com.jugador.service.controllers.dtos.JugadorResponseDTO;
import com.jugador.service.controllers.dtos.LesionResponseDTO;
import com.jugador.service.controllers.dtos.VincularJugadorDTO;
import com.jugador.service.controllers.payload.ApiResponse;
import com.jugador.service.exception.ResourceAlreadyExistsException;
import com.jugador.service.exception.ResourceNotFoundException;
import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;
import com.jugador.service.models.JugadorLesion;
import com.jugador.service.models.Lesion;
import com.jugador.service.services.JugadorLesionService;
import com.jugador.service.services.JugadorService;
import com.jugador.service.services.LesionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("jugador")
@RequiredArgsConstructor
@Slf4j
public class JugadorController {

    // Services
    private final JugadorService jugadorService;
    private final LesionService lesionService;
    private final JugadorLesionService jugadorLesionService;

    @GetMapping("/find/{jugadorId}")
    public ResponseEntity<ApiResponse> getJugadorById(@PathVariable Long jugadorId) {

        // Obtenemos el jugador
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);

        // Verificaciones de identidad
        if (jOptional.isEmpty()){
            log.warn("Jugador con identificador {} no identificado", jugadorId);
            throw new ResourceNotFoundException("Jugador", "identificador", jugadorId);
        }

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.jugadorToResponseDTO(jOptional.get()))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createJugador(@RequestBody @Valid JugadorDTO jDto) {

        // Verificaciones antes de crear el registro
        if (this.jugadorService.findByEmail(jDto.email()).isPresent()){
            log.warn("Intento de registro de correo ya existente: {}", jDto.email());
            throw new ResourceAlreadyExistsException("Jugador", "email", jDto.email());
        }

        if (this.jugadorService.findByDocumento(jDto.documento()).isPresent()){
            log.warn("Intento de registro de documento ya existente: {}", jDto.documento());
            throw new ResourceAlreadyExistsException("Jugador", "documento", jDto.documento());
        }

        // Creamos el nuevo jugador con la información proporcionada
        Jugador jugador = Jugador
                .builder()
                .nombre(jDto.nombre())
                .apellido(jDto.apellido())
                .fecha_nacimiento(jDto.fecha_nacimiento())
                .documento(jDto.documento())
                .email(jDto.email())
                .direccion(jDto.direccion())
                .celular(jDto.celular())
                .estado(EstadoJugador.ACTIVO)
                .numero_camiseta(jDto.numero_camiseta())
                .tipo_sangre(jDto.tipo_sangre())
                .nivel_hemoglobina(jDto.nivel_hemoglobina())
                .consumo_o2(jDto.consumo_o2())
                .lactato_sangre(jDto.lactato_sangre())
                .equipoId(jDto.equipoId())
                .build();
        
        this.jugadorService.save(jugador);

        log.info("POST: Jugador {}", jDto);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Nuevo jugador registrado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/update/{jugadorId}")
    public ResponseEntity<ApiResponse> updateJugador(@RequestBody @Valid JugadorDTO jDto, @PathVariable Long jugadorId) {

        // Obtenemos el jugador
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);

        // Verificaciones de identidad
        if (jOptional.isEmpty()){
            log.warn("Jugador con identificador {} no identificado", jugadorId);
            throw new ResourceNotFoundException("Jugador", "identificador", jugadorId);
        }

        // Verificaciones antes de actualizar el registro
        if (this.jugadorService.findByEmail(jDto.email()).isPresent() && !jDto.email().equals(jOptional.get().getEmail())){
            log.warn("Intento de registro de correo ya existente: {}", jDto.email());
            throw new ResourceAlreadyExistsException("Jugador", "email", jDto.email());
        }

        if (this.jugadorService.findByDocumento(jDto.documento()).isPresent() && !jDto.documento().equals(jOptional.get().getDocumento())){
            log.warn("Intento de registro de documento ya existente: {}", jDto.documento());
            throw new ResourceAlreadyExistsException("Jugador", "documento", jDto.documento());
        }

        Jugador jugador = jOptional.get();

        // En caso de cambiar el estado
        if (jugador.getEstado().toString().equals("LESIONADO") && jDto.estado().equals("activo") && this.hasActiveLesiones(jugador)){
            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("No es posible activar nuevamente al jugador, aún mantiene lesiones activas")
                .data("No data provided")
                .build()
            );
        }

        // Actualizamos la data del jugador
        jugador.setNombre(jDto.nombre());
        jugador.setApellido(jDto.apellido());
        jugador.setFecha_nacimiento(jDto.fecha_nacimiento());
        jugador.setDocumento(jDto.documento());
        jugador.setEmail(jDto.email());
        jugador.setDireccion(jDto.direccion());
        jugador.setCelular(jDto.celular());
        jugador.setEstado(EstadoJugador.valueOf(jDto.estado().toUpperCase()));
        jugador.setNumero_camiseta(jDto.numero_camiseta());
        jugador.setTipo_sangre(jDto.tipo_sangre());
        jugador.setNivel_hemoglobina(jDto.nivel_hemoglobina());
        jugador.setConsumo_o2(jDto.consumo_o2());
        jugador.setLactato_sangre(jDto.lactato_sangre());

        // En caso que el jugador cambie a estado retirado lo desvinculamos del equipo
        if (jDto.estado().equals("retirado")){
            jugador.setEquipoId(null);
            log.warn("El jugador {} {} se ha retirado de las canchas", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(true)
                .code(200)
                .message("Estado de jugador actualizado: Retirado de las canchas")
                .data("No data provided")
                .build()
            );
        }

        // Registramos los cambios
        this.jugadorService.save(jugador);

        log.info("PUT: Jugador {}", jugador);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Jugador actualizado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/link")
    public ResponseEntity<ApiResponse> linkJugador(@RequestBody @Valid VincularJugadorDTO vDto){

        // Obtenemos el jugador
        Optional<Jugador> jOptional = this.jugadorService.findByEmail(vDto.email());

        // Verificaciones de identidad
        if (jOptional.isEmpty()){
            log.warn("Jugador con correo {} no identificado", vDto.email());
            throw new ResourceNotFoundException("Jugador", "correo", vDto.email());
        }

        // Verificamos que el jugador a ingresar no se encuentre retirado o 
        Jugador jugador = jOptional.get();

        if (jugador.getEstado().toString().equals("RETIRADO")){
            log.warn("El jugador {} {} se ha retirado de las canchas y no puede ser vinculado a un equipo", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("El jugador especificado se encuentra retirado")
                .data("No data provided")
                .build()
            );
        }

        if (jugador.getEstado().toString().equals("LESIONADO")){
            log.warn("El jugador {} {} se encuentra lesionado y no puede ser vinculado a un equipo", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("El jugador especificado se encuentra lesionado")
                .data("No data provided")
                .build()
            );
        }

        // Registramos los cambios
        jugador.setEquipoId(vDto.equipoId());
        jugador.setEstado(EstadoJugador.ACTIVO);
        this.jugadorService.save(jugador);

        log.info("Jugador vinculado: {}", jugador);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Jugador vinculado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/unlink/{jugadorId}")
    public ResponseEntity<ApiResponse> unlinkJugador(@PathVariable Long jugadorId){

        // Obtenemos el jugador
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);

        // Verificaciones de identidad
        if (jOptional.isEmpty()){
            log.warn("Jugador con identificador {} no identificado", jugadorId);
            throw new ResourceNotFoundException("Jugador", "identificador", jugadorId);
        }

        // Finalizamos la vigencia del contrato del integrante
        Jugador jugador = jOptional.get();
        Long equipo = jugador.getEquipoId();

        // En caso de intentar finalizar la vigencia de un integrante ya desvinculado
        if (jugador.getEquipoId() == null) {
            log.info("Intento de finalizar la vigencia de un jugador sin ninguna afiliacion registada: {} {}", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.badRequest().body(ApiResponse
                                .builder()
                                .flag(false)
                                .code(400)
                                .message("El jugador especificado esta retirado o no se encuentra vinculado a ningún equipo")
                                .data("No data provided")
                                .build()
                            );
        }

        // En caso de intentar desvincular a un jugador lesionado
        if (this.hasActiveLesiones(jugador)){
            log.warn("Intento de desvincular a un jugador que esta actualmente lesionado: {} {}", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.badRequest().body(ApiResponse
                                .builder()
                                .flag(false)
                                .code(400)
                                .message("El jugador especificado no puede ser desvinculado: Se encuentra actualmente lesionado")
                                .data("No data provided")
                                .build()
                            );
        }

        // Desvinculamos al integrante en especifico y cambiamos su estado
        jugador.setEquipoId(null);
        jugador.setEstado(EstadoJugador.INACTIVO);

        // Guardamos los cambios
        this.jugadorService.save(jugador);

        log.info("Desvinculación del jugador con identificador {}, del equipo {}", jugadorId, equipo);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Desvinculación del jugador realizada correctamente")
            .data("No data provided")
            .build()
        );

    }

    @DeleteMapping("/delete/{jugadorId}")
    public ResponseEntity<ApiResponse> deleteJugador(@PathVariable Long jugadorId) {

        // Obtenemos el jugador
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);

        // Verificaciones de identidad
        if (jOptional.isEmpty()){
            log.warn("Jugador con identificador {} no identificado", jugadorId);
            throw new ResourceNotFoundException("Jugador", "identificador", jugadorId);
        }

        // No permitimos la eliminación de un jugador con vinculación vigente
        if (jOptional.get().getEquipoId() != null){
            log.info("Intento de eliminación del jugador con identificador {} con vinculación vigente", jugadorId);
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                                .flag(false)
                                .code(400)
                                .message("El jugador especificado aún se encuentra vinculado a un equipo")
                                .data("No data provided")
                                .build());
        }

        // Eliminamos al jugador en cuestión
        this.jugadorService.deleteById(jugadorId);

        log.info("DELETE: Jugador con identificador {}", jugadorId);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Jugador eliminado correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Endpoints para la asignación de lesiones a jugadores

    @GetMapping("/getLesiones/{jugadorId}")
    public ResponseEntity<ApiResponse> getLesionesJugador(@PathVariable Long jugadorId){

        // Obtenemos el jugador
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);

        // Verificaciones de identidad
        if (jOptional.isEmpty()){
            log.warn("Jugador con identificador {} no identificado", jugadorId);
            throw new ResourceNotFoundException("Jugador", "identificador", jugadorId);
        }

        // Obtenemos las lesiones
        List<LesionResponseDTO> lesiones = jOptional.get().getLesiones()
                .stream().map(data -> this.lesionToResponseDTO(data.getLesion())).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Lesiones obtenidas correctamente")
            .data(lesiones)
            .build()
        );

    }

    @PostMapping("/assignLesion/{jugadorId}")
    public ResponseEntity<ApiResponse> asignarLesion(@RequestBody @Valid JugadorLesionDTO jLesionDTO, @PathVariable Long jugadorId){

        // Verificaciones antes de crear el registro
        if (jLesionDTO.fecha_inicio().isEqual(jLesionDTO.fecha_fin())){
            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("Las fechas de inicio y recuperación de la lesión no pueden coincidir")
                .data("No data provided")
                .build()
            );
        }

        if (jLesionDTO.fecha_inicio().isAfter(LocalDate.now()) || jLesionDTO.fecha_fin().isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("Las fechas de inicio y recuperación de la lesión no son válidas")
                .data("No data provided")
                .build()
            );
        }

        if (jLesionDTO.fecha_inicio().isAfter(jLesionDTO.fecha_fin())){
            return ResponseEntity.badRequest().body(ApiResponse
                .builder()
                .flag(false)
                .code(400)
                .message("Las fecha de inicio no puede proceder a la fecha de recuperación de la lesión")
                .data("No data provided")
                .build()
            );
        }

        // Obtemos al jugador y la lesión pertinentes
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);
        Optional<Lesion> lOptional = this.lesionService.findById(jLesionDTO.lesionId());

        // Creamos el registro con los datos obtenidos
        JugadorLesion jLesion = JugadorLesion
                .builder()
                .jugador(jOptional.get())
                .lesion(lOptional.get())
                .fecha_inicio(jLesionDTO.fecha_inicio())
                .fecha_fin(jLesionDTO.fecha_fin())
                .build();
        
        this.jugadorLesionService.save(jLesion);

        // Cambiamos el estado del jugador a lesionado
        Jugador jugador = jOptional.get();
        jugador.setEstado(EstadoJugador.LESIONADO);
        this.jugadorService.save(jugador);

        log.info("Asignando lesión al Jugador con identificador {}", jugadorId);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Lesión asignada correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Endpoints para ser consumidos por otros MS

    @GetMapping("/findByEquipo/{equipoId}")
    public ResponseEntity<?> findByEquipo(@PathVariable Long equipoId){
        return ResponseEntity.ok(this.jugadorService.findByEquipo(equipoId)
                        .stream()
                        .map(this::jugadorToResponseDTO).toList());
    }

    @GetMapping("/findByEquipo/{equipoId}/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable Long equipoId, @PathVariable String estado) { 

        // Verificación de no toparse con estados no válidos
        EstadoJugador eJugador = EstadoJugador.valueOf(estado.toUpperCase());

        // Obtenemos la lista de los jugadores de acuerdo a su estado
        List<JugadorResponseDTO> jList = this.jugadorService.findByEstado(eJugador)
                .stream().map(this::jugadorToResponseDTO).toList();
        
        // Filtramos los jugadores por el equipo especifico
        List<JugadorResponseDTO> jListFiltrada = jList.stream()
                .filter(jugador -> jugador.getId().equals(equipoId)).toList();

        return ResponseEntity.ok(jListFiltrada);

    }


    // Converter methods

    private JugadorResponseDTO jugadorToResponseDTO(Jugador jugador) {

        return JugadorResponseDTO.builder()
                .id(jugador.getId())
                .nombre(jugador.getNombre())
                .apellido(jugador.getApellido())
                .fecha_nacimiento(jugador.getFecha_nacimiento().toString())
                .documento(jugador.getDocumento())
                .email(jugador.getEmail())
                .direccion(jugador.getDireccion())
                .celular(jugador.getCelular())
                .estado(jugador.getEstado().toString().toLowerCase())
                .numero_camiseta(jugador.getNumero_camiseta())
                .tipo_sangre(jugador.getTipo_sangre())
                .nivel_hemoglobina(jugador.getNivel_hemoglobina())
                .consumo_o2(jugador.getConsumo_o2())
                .lactato_sangre(jugador.getLactato_sangre())
                .equipoId(jugador.getEquipoId())
                .build();

    }

    private LesionResponseDTO lesionToResponseDTO(Lesion lesion) {

        return LesionResponseDTO.builder()
                .id(lesion.getId())
                .nombre(lesion.getNombre())
                .tratamiento(lesion.getTratamiento())
                .observaciones(lesion.getObservaciones())
                .build();

    }


    // Aux methods

    private boolean hasActiveLesiones(Jugador jugador) {

        // Obtenemos las lesiones del jugador
        List<JugadorLesion> jLesions = jugador.getLesiones();

        // Registramos la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Verificamos si alguna de las lesion aún se enuentra activa
        return jLesions.stream()
                    .anyMatch(lesion -> lesion.getFecha_fin().isAfter(fechaActual));

    }
    
}
