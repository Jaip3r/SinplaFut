package com.club.service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.club.service.controllers.DTO.ClubDTO;
import com.club.service.controllers.DTO.ClubResponseDTO;
import com.club.service.controllers.DTO.TeamDTO;
import com.club.service.controllers.Payload.ApiResponse;
import com.club.service.exception.AssociatedEntitiesException;
import com.club.service.exception.ResourceAlreadyExistsException;
import com.club.service.exception.ResourceNotFoundException;
import com.club.service.exception.annotation.ValidFile;
import com.club.service.models.Club;
import com.club.service.services.CloudinaryService;
import com.club.service.services.ClubService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("club")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Slf4j
public class ClubController {

    // Services
    private final ClubService clubService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> getClubs() {
        
        // Obtenemos la lista de todos los clubes registrados
        List<ClubResponseDTO> clubList = this.clubService.findAll()
                .stream().map(this::clubToResponseDTO).toList();

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(clubList)
            .build()
        );

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResponse> getClubById(@PathVariable Long id) {
        
        // Obtenemos el club
        Club club = this.clubService.findById(id);

        // Verificaciones de identidad
        if (club == null){
            log.warn("Club con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Club", "identificador", id);
        }

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Info obtenida correctamente")
            .data(this.clubToResponseDTO(club))
            .build()
        );

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClub(
        @RequestParam("nombre") String nombre,
        @RequestParam("direccion") String direccion,
        @RequestParam("telefono") String telefono,
        @RequestParam("ciudad") String ciudad,
        @RequestParam("pais") String pais,
        @RequestParam("estadio") String estadio, 
        @RequestParam("file") @Valid @ValidFile(message = "Solo se admiten archivos de imagen .png") MultipartFile file,
        @Valid ClubDTO clubDTO
    ) throws IOException{

        // Verificaciones antes de crear el registro
        if (this.clubService.findByStadium(estadio) != null){
            log.warn("Intento de registro de un estadio ya existente: {}", estadio);
            throw new ResourceAlreadyExistsException("Estadio", "Nombre", estadio);
        }

        // Cargamos el archivo
        Map<String, String> map = this.cloudinaryService.uploadFile(file, nombre.trim());

        // Creamos el club con la información proporcionada
        Club club = Club
            .builder()
            .nombre(nombre)
            .direccion(direccion)
            .telefono(telefono)
            .ciudad(ciudad)
            .pais(pais)
            .estadio(estadio)
            .logoUrl(map.get("secure_url"))
            .logoId(map.get("public_id"))
            .build();
            
        this.clubService.save(club);

        log.info("POST: club {}", club);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Club registrado correctamente")
            .data("No data provided")
            .build()
        );
           
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateClub(
        @PathVariable Long id,
        @RequestParam("nombre") String nombre,
        @RequestParam("direccion") String direccion,
        @RequestParam("telefono") String telefono,
        @RequestParam("ciudad") String ciudad,
        @RequestParam("pais") String pais,
        @RequestParam("estadio") String estadio, 
        @RequestParam(required = false, name = "file") @Valid @ValidFile(message = "Solo se admiten archivos de imagen .png") MultipartFile file,
        @Valid ClubDTO clubDTO
    ) throws IOException{

        // Obtenemos el club
        Club club = this.clubService.findById(id);

        if (club == null){
            log.warn("Club con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Club", "identificador", id);
        }

        if (this.clubService.findByStadium(estadio) != null && !estadio.toLowerCase().equals(club.getEstadio().toLowerCase())){
            log.warn("Intento de registro de un estadio ya existente: {}", estadio);
            throw new ResourceAlreadyExistsException("Estadio", "Nombre", estadio);
        }

        // Actualizamos la data del club
        club.setNombre(nombre);
        club.setDireccion(direccion);
        club.setTelefono(telefono);
        club.setCiudad(ciudad);
        club.setPais(pais);
        club.setEstadio(estadio);

        // Actualizamos el escudo si se provee un archivo
        if (file != null){

            Map<String, String> result = new HashMap<String, String>();

            // Obtenemos el id de la imagen original
            String original_file_id = club.getLogoId().split("/")[1];

            if (!original_file_id.equals(nombre)){

                // Eliminamos el escudo relacionado al anterior nombre
                this.cloudinaryService.delete(club.getLogoId());

            }

            // Cargamos el archivo con el nuevo escudo
            result = this.cloudinaryService.uploadFile(file, nombre);
            
            club.setLogoUrl(result.get("secure_url"));
            club.setLogoId(result.get("public_id"));
            
        }
        
        // Guardamos los cambios    
        this.clubService.save(club);

        log.info("PUT: club {}", club);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Club actualizado correctamente")
            .data("No data provided")
            .build()
        );
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteClub(@PathVariable Long id) throws IOException{

        // Obtenemos el club
        Club club = this.clubService.findById(id);

        if (club == null){
            log.warn("Club con identificador {} no identificado", id);
            throw new ResourceNotFoundException("Club", "identificador", id);
        }

        // Varificamos que el club no tenga equipos asociados
        if (this.clubService.findTeamsByClubId(id).size() > 0){
            log.warn("Intento de eliminación de club con equipos asociados: {}", club.getNombre());
            throw new AssociatedEntitiesException("Club", club.getNombre(), "equipos");
        }

        // Eliminamos el logo asociado
        this.cloudinaryService.delete(club.getLogoId());

        // Eliminamos el club
        this.clubService.deleteById(id);

        log.info("DELETE: club {}", club);
        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Club eliminado correctamente")
            .data("No data provided")
            .build()
        );

    }


    // Comunicación con otros MS

    @GetMapping("/findTeams/{clubId}")
    public ResponseEntity<ApiResponse> findTeams(@PathVariable Long clubId){

        // Obtenemos el club
        Club club = this.clubService.findById(clubId);

        if (club == null){
            log.warn("Club con identificador {} no identificado", clubId);
            throw new ResourceNotFoundException("Club", "identificador", clubId);
        }

        // Obtenemos una lista de los equipos asociados a el club
        List<TeamDTO> teams = this.clubService.findTeamsByClubId(clubId);

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Información de equipos obtenida correctamente")
            .data(teams)
            .build()
        );

    }


    // Converter methods

    private ClubResponseDTO clubToResponseDTO(Club club) {

        return ClubResponseDTO.builder()
                .id(club.getId())
                .nombre(club.getNombre())
                .direccion(club.getDireccion())
                .telefono(club.getTelefono())
                .ciudad(club.getCiudad())
                .pais(club.getPais())
                .estadio(club.getEstadio())
                .logoUrl(club.getLogoUrl())
                .build();

    }
    

    
}
