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
import com.jugador.service.controllers.dtos.JugadorLesionResponseDTO;
import com.jugador.service.controllers.dtos.JugadorResponseDTO;
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

        return ResponseEntity.ok(createApiResponse(true, 200, "Info obtenida correctamente", this.jugadorToResponseDTO(jOptional.get())));

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createJugador(@RequestBody @Valid JugadorDTO jDto) {

        // Verificaciones antes de crear el registro
        if (this.jugadorService.findAllByEmailOrDocumentoOrCamiseta(jDto.email(), jDto.documento(), jDto.numero_camiseta()).size() == 1){
            log.warn("Intento de registro de correo/documento/camiseta ya existente: {} / {} / {}", jDto.email(), jDto.documento(), jDto.numero_camiseta());
            throw new ResourceAlreadyExistsException("Jugador", "email/documento/camiseta", "");
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
        return ResponseEntity.ok(createApiResponse(true, 200, "Nuevo jugador registrado correctamente", "No data provided"));

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
        List<Jugador> lJugadors = this.jugadorService.findAllByEmailOrDocumentoOrCamiseta(jDto.email(), jDto.documento(), jDto.numero_camiseta());
        Jugador jugador = jOptional.get();

        if (lJugadors.size() > 1){
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Favor verificar que el correo, el documento o la camiseta no se encuentren ya registrados", "No data provided"));
        }

        if (lJugadors.size() == 1) {
        
            if (!jDto.email().equals(jugador.getEmail())) {
                log.warn("Intento de registro de correo ya existente: {}", jDto.email());
                throw new ResourceAlreadyExistsException("Jugador", "email", jDto.email());
            }
        
            if (!jDto.documento().equals(jugador.getDocumento())) {
                log.warn("Intento de registro de documento ya existente: {}", jDto.documento());
                throw new ResourceAlreadyExistsException("Jugador", "documento", jDto.documento());
            }
        
            if (jDto.numero_camiseta() != jugador.getNumero_camiseta()) {
                throw new ResourceAlreadyExistsException("Jugador", "numero_camiseta", jDto.numero_camiseta());
            }
        }

        // En caso de intentar cambiar el estado lesionado a activo y posee lesiones activas
        if (jugador.getEstado().toString().equals("LESIONADO") && jDto.estado().equals("activo") && this.hasActiveLesiones(jugador)){
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "No es posible activar nuevamente al jugador, aún mantiene lesiones activas", "No data provided"));
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
            jugador.setEstado(EstadoJugador.RETIRADO);

            log.warn("El jugador {} {} se ha retirado de las canchas", jugador.getNombre(), jugador.getApellido());

        }

        // Registramos los cambios
        this.jugadorService.save(jugador);

        log.info("PUT: Jugador {}", jugador);
        return ResponseEntity.ok(createApiResponse(true, 200, "Jugador actualizado correctamente", "No data provided"));

    }

    @PutMapping("/link")
    public ResponseEntity<ApiResponse> linkJugador(@RequestBody @Valid VincularJugadorDTO vDto){

        // Obtenemos el jugador
        List<Jugador> jList = this.jugadorService.findAllByEmailOrDocumentoOrCamiseta(vDto.email(), "", 0);

        // Verificaciones de identidad
        if (jList.size() == 0){
            log.warn("Jugador con correo {} no identificado", vDto.email());
            throw new ResourceNotFoundException("Jugador", "correo", vDto.email());
        }

        // Verificamos que el jugador a ingresar no se encuentre retirado 
        Jugador jugador = jList.get(0);

        if (jugador.getEstado().toString().equals("RETIRADO")){
            log.warn("El jugador {} {} se ha retirado de las canchas y no puede ser vinculado a un equipo", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "El jugador especificado se encuentra retirado", "No data provided"));
        }

        // En caso de tratar de vincular a un jugador con vinculación vigente a otro equipo
        if (jugador.getEquipoId() != null){
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "El jugador especificado ya se encuentra vinculado a un equipo", "No data provided"));
        }

        // Registramos los cambios
        jugador.setEquipoId(vDto.equipoId());
        jugador.setEstado(EstadoJugador.ACTIVO);
        this.jugadorService.save(jugador);

        log.info("Jugador vinculado: {}", jugador);
        return ResponseEntity.ok(createApiResponse(true, 200, "Nuevo jugador vinculado correctamente", "No data provided"));

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
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "El jugador especificado esta retirado o no se encuentra vinculado a ningún equipo", "No data provided"));
        }

        // En caso de intentar desvincular a un jugador lesionado
        if (hasActiveLesiones(jugador)){
            log.warn("Intento de desvincular a un jugador que esta actualmente lesionado: {} {}", jugador.getNombre(), jugador.getApellido());
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "El jugador especificado se encuentra actualmente lesionado y no puede ser desvinculado", "No data provided"));
        }

        // Desvinculamos al integrante en especifico y cambiamos su estado
        jugador.setEquipoId(null);
        jugador.setEstado(EstadoJugador.INACTIVO);

        // Guardamos los cambios
        this.jugadorService.save(jugador);

        log.info("Desvinculación del jugador con identificador {}, del equipo {}", jugadorId, equipo);
        return ResponseEntity.ok(createApiResponse(true, 200, "Jugador desvinculado correctamente", "No data provided"));

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

        // Eliminamos al jugador en cuestión
        this.jugadorService.deleteById(jugadorId);

        log.info("DELETE: Jugador con identificador {}", jugadorId);
        return ResponseEntity.ok(createApiResponse(true, 200, "Jugador eliminado correctamente", "No data provided"));

    }


    // Endpoints para el control de  las lesiones de jugadores

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
        List<JugadorLesionResponseDTO> lesiones = jOptional.get().getLesiones()
                .stream().map(this::jugadorLesionToResponseDTO).toList();

        return ResponseEntity.ok(createApiResponse(true, 200, "Lesiones obtenidas correctamente", lesiones));

    }

    @GetMapping("/getLesion/{id}")
    public ResponseEntity<ApiResponse> getLesion(@PathVariable Long id){

        // Obtenemos el registro
        Optional<JugadorLesion> jOptional = this.jugadorLesionService.findById(id);

        // Verificación de integridad
        if (jOptional.isEmpty()){
            log.warn("Registro de lesion asignada ajugador con identificador {} no identificada", id);
            throw new ResourceNotFoundException("Lesión", "identificador", id);
        }

        return ResponseEntity.ok(createApiResponse(true, 200, "Lesiones obtenidas correctamente", jugadorLesionToResponseDTO(jOptional.get())));

    }

    @PostMapping("/assignLesion/{jugadorId}")
    public ResponseEntity<ApiResponse> asignarLesion(@RequestBody @Valid JugadorLesionDTO jLesionDTO, @PathVariable Long jugadorId){

        LocalDate fechaInicio = jLesionDTO.fecha_inicio();
        LocalDate fechaFin = jLesionDTO.fecha_fin();
        LocalDate now = LocalDate.now();

        // Verificaciones antes de crear el registro
        if (fechaInicio.isEqual(fechaFin)) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y recuperación de la lesión no pueden coincidir", "No data provided"));
        }

        if (fechaInicio.isAfter(now) || fechaFin.isBefore(now) || fechaInicio.isAfter(fechaFin)) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y recuperación de la lesión no son válidas", "No data provided"));
        }

        // Obtemos al jugador y la lesión pertinentes
        Optional<Jugador> jOptional = this.jugadorService.findById(jugadorId);
        Optional<Lesion> lOptional = this.lesionService.findById(jLesionDTO.lesionId());

        // Verificamos que el registro de esa lesión no sea repetido
        if (this.jugadorLesionService.findJugadorLesion(jOptional.get(), lOptional.get(), fechaInicio, fechaFin).isPresent()){
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "El jugador ya posee este registro de lesión", "No data provided"));
        }

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
        return ResponseEntity.ok(createApiResponse(true, 200, "Lesión asignada correctamente", "No data provided"));

    }

    @PutMapping("/updateLesion/{id}")
    public ResponseEntity<ApiResponse> actualizarLesion(@RequestBody @Valid JugadorLesionDTO jLesionDTO, @PathVariable Long id){

        // Obtenemos el registro
        Optional<JugadorLesion> jOptional = this.jugadorLesionService.findById(id);

        // Verificación de integridad
        if (jOptional.isEmpty()){
            log.warn("Registro de lesion asignada ajugador con identificador {} no identificada", id);
            throw new ResourceNotFoundException("Lesión", "identificador", id);
        }

        // Verificaciones de datos
        LocalDate fechaInicio = jLesionDTO.fecha_inicio();
        LocalDate fechaFin = jLesionDTO.fecha_fin();
        LocalDate now = LocalDate.now();

        if (fechaInicio.isEqual(fechaFin)) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y recuperación de la lesión no pueden coincidir", "No data provided"));
        }

        if (fechaInicio.isAfter(now) || fechaInicio.isAfter(fechaFin)) {
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "Las fechas de inicio y recuperación de la lesión no son válidas", "No data provided"));
        }

        // Obtemos la lesión pertinente
        Optional<Lesion> lOptional = this.lesionService.findById(jLesionDTO.lesionId());

        // Verificamos que el registro de esa lesión no sea repetido    
        Optional<JugadorLesion> jLesionOptional = this.jugadorLesionService.findJugadorLesion(jOptional.get().getJugador(), lOptional.get(), fechaInicio, fechaFin);

        if (jLesionOptional.isPresent() && !jLesionOptional.get().getId().equals(id)){
            return ResponseEntity.badRequest().body(createApiResponse(false, 400, "El jugador ya posee este registro de lesión", "No data provided"));
        }

        // Actualizamos los campos y registramos los cambios
        JugadorLesion jLesion = jOptional.get();

        jLesion.setLesion(lOptional.get());
        jLesion.setFecha_inicio(fechaInicio);
        jLesion.setFecha_fin(fechaFin);
        
        this.jugadorLesionService.save(jLesion);

        log.info("lesión {} del Jugador {} actualizada correctamente", jLesion.getId(), jLesion.getJugador().getId());
        return ResponseEntity.ok(createApiResponse(true, 200, "Lesión de jugador actualizada correctamente", "No data provided"));

    }

    @DeleteMapping("/deleteLesion/{id}")
    public ResponseEntity<ApiResponse> deleteLesionAsignada(@PathVariable Long id){

        // Obtenemos el registro
        Optional<JugadorLesion> jOptional = this.jugadorLesionService.findById(id);

        // Verificación de integridad
        if (jOptional.isEmpty()){
            log.warn("Registro de lesion asignada ajugador con identificador {} no identificada", id);
            throw new ResourceNotFoundException("Lesión", "identificador", id);
        }

        // Eliminamos el registro en cuestión
        this.jugadorLesionService.deleteById(id);

        return ResponseEntity.ok(createApiResponse(true, 200, "Lesión desasignada correctamente", "No data provided"));

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
        List<JugadorResponseDTO> jList = this.jugadorService.findByEstado(eJugador, equipoId)
                .stream().map(this::jugadorToResponseDTO).toList();

        return ResponseEntity.ok(jList);

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

    private JugadorLesionResponseDTO jugadorLesionToResponseDTO(JugadorLesion jLesion) {

        return JugadorLesionResponseDTO.builder()
                .id(jLesion.getId())
                .lesion(jLesion.getLesion().getNombre())
                .fecha_inicio(jLesion.getFecha_inicio().toString())
                .fecha_fin(jLesion.getFecha_fin().toString())
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

    private ApiResponse createApiResponse(boolean flag, int code, String message, Object data){
        return ApiResponse.builder()
                .flag(flag)
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
    
}
