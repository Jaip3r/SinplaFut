package com.team.service.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team.service.controllers.dtos.TeamDTO;
import com.team.service.controllers.dtos.TeamResponseDTO;
import com.team.service.controllers.payload.ApiResponse;
import com.team.service.exception.ResourceAlreadyExistsException;
import com.team.service.exception.ResourceNotFoundException;
import com.team.service.exception.annotation.ValidFile;
import com.team.service.models.Category;
import com.team.service.models.Team;
import com.team.service.services.CloudinaryService;
import com.team.service.services.TeamService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("team")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    // Services
    private final TeamService teamService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> getTeams(){

        // Obtenemos una lista de todos los clubes registrados
        List<TeamResponseDTO> teamList = this.teamService.findAll()
                .stream()
                .map(this::teamToResponseDTO)
                .toList();
        
        return ResponseEntity.ok(
            ApiResponse.builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(teamList)
            .build()
        );

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getTeamById(@PathVariable Long id){
        
        // Obtenemos el equipo
        Team equipo = this.teamService.findById(id);

        // Verificaciones de identidad
        if (equipo == null){
            log.warn("Equipo con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Equipo", "identificador", id);
        }

        return ResponseEntity.ok(
            ApiResponse.builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.teamToResponseDTO(equipo))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTeam(
        @RequestParam("nombre") String nombre,
        @RequestParam("telefono") String telefono,
        @RequestParam("categoria") String categoria,
        @RequestParam("clubId") Long clubId,
        @RequestParam("escudo") @Valid @ValidFile(message = "Solo se admiten archivos de imagen .png") MultipartFile file,
        @Valid TeamDTO teamDTO
    ) throws IOException{

        if (this.teamService.findByName(nombre) != null){
            log.warn("Intento de registro de un equipo ya existente");
            throw new ResourceAlreadyExistsException("Equipo", "nombre", nombre);
        }

        // Cargamos el archivo
        Map<String, String> map = this.cloudinaryService.uploadFile(file, nombre);

        // Creamos el equipo con la información proporcionada
        Team equipo = Team.builder()
            .nombre(nombre)
            .telefono(telefono)
            .categoria(Category.valueOf(categoria.toUpperCase()))
            .clubId(clubId)
            .escudoUrl(map.get("secure_url"))
            .escudoId(map.get("public_id"))
            .build();
        
        this.teamService.save(equipo);

        log.info("POST: equipo {}", equipo);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Equipo registrado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTeam(
        @PathVariable Long id,
        @RequestParam("nombre") String nombre,
        @RequestParam("telefono") String telefono,
        @RequestParam("categoria") String categoria,
        @RequestParam("clubId") Long clubId,
        @RequestParam(required = false, name = "escudo") @Valid @ValidFile(message = "Solo se admiten archivos de imagen .png") MultipartFile file,
        @Valid TeamDTO teamDTO
    ) throws IOException{

        // Obtenemos el equipo
        Team equipo = this.teamService.findById(id);

        if (equipo == null){
            log.warn("Equipo con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Equipo", "identificador", id);
        }

        String nombreOriginal = equipo.getNombre();

        if (this.teamService.findByName(nombre) != null && !nombreOriginal.equals(nombre)){
            log.warn("Intento de registro de un equipo ya existente");
            throw new ResourceAlreadyExistsException("Equipo", "nombre", nombre);
        }

        // Actualizamos la data del equipo
        equipo.setNombre(nombre);
        equipo.setTelefono(telefono);
        equipo.setCategoria(Category.valueOf(categoria.toUpperCase()));
        equipo.setClubId(clubId);

        // Actualizamos el escudo si se provee un archivo
        if (file != null){

            Map<String, String> result = new HashMap<String, String>();

            // Obtenemos el id de la imagen original
            String original_file_id = equipo.getEscudoId().split("/")[1];

            if (!original_file_id.equals(nombre)){

                // Cargamos el archivo con el nuevo escudo
                result = this.cloudinaryService.uploadFile(file, nombre);

                // Eliminamos el escudo relacionado al anterior nombre
                this.cloudinaryService.delete(original_file_id);

            }else{

                result = this.cloudinaryService.uploadFile(file, nombre);

            }

            equipo.setEscudoUrl(result.get("secure_url"));
            equipo.setEscudoId(result.get("public_id"));
            
        }

        // Registramos los cambios
        this.teamService.save(equipo);

        log.info("PUT: equipo {}", equipo);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Equipo actualizado correctamente")
            .data("No data provided")
            .build()
        );

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTeam(@PathVariable Long id) throws IOException{

        // Obtenemos el equipo
        Team equipo = this.teamService.findById(id);

        if (equipo == null){
            log.warn("Equipo con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Equipo", "identificador", id);
        }

        // Eliminamos el escudo asociado
        this.cloudinaryService.delete(equipo.getEscudoId());

        // Eliminamos el equipo
        this.teamService.delete(id);;

        log.info("DELETE: equipo {}", equipo);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Equipo eliminado correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Converter methods

    private TeamResponseDTO teamToResponseDTO(Team team){

        return TeamResponseDTO
            .builder()
            .id(team.getId())
            .nombre(team.getNombre())
            .telefono(team.getTelefono())
            .categoria(team.getCategoria().toString().toLowerCase())
            .escudo(team.getEscudoUrl())
            .build();

    }

    
}
