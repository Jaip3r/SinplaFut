package com.club.service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.club.service.controllers.DTO.ClubDTO;
import com.club.service.controllers.DTO.ClubResponseDTO;
import com.club.service.controllers.Payload.ApiResponse;
import com.club.service.exception.ResourceAlreadyExistsException;
import com.club.service.exception.ResourceNotFoundException;
import com.club.service.exception.annotation.ValidFile;
import com.club.service.models.Club;
import com.club.service.services.CloudinaryService;
import com.club.service.services.ClubService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("club")
@RequiredArgsConstructor
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
            throw new ResourceAlreadyExistsException("Estadio", "Nombre", estadio);
        }

        // Cargamos el archivo
        Map<String, String> map = this.cloudinaryService.uploadFile(file, nombre);

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

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Club registrado correctamente")
            .data(club)
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
        @RequestParam("file") @Valid @ValidFile(message = "Solo se admiten archivos de imagen .png") MultipartFile file,
        @Valid ClubDTO clubDTO
    ) throws IOException{

        // Obtenemos el club
        Club club = this.clubService.findById(id);
        String nombreOriginal = club.getNombre();

        // Verificaciones antes de crear el registro
        if (this.clubService.findByStadium(estadio) != null && !estadio.toLowerCase().equals(club.getEstadio())){
            throw new ResourceAlreadyExistsException("Estadio", "Nombre", estadio);
        }

        // Actualizamos la data del club
        club.setNombre(nombre);
        club.setDireccion(direccion);
        club.setTelefono(telefono);
        club.setCiudad(ciudad);
        club.setPais(pais);
        club.setEstadio(estadio);

        // Actualizamos el logo si se provee un archivo
        if (file == null){
            Map<String, String> result = this.cloudinaryService.updateFile(file, nombreOriginal);
            club.setLogoUrl(result.get("secure_url"));
            club.setLogoId(result.get("public_id"));
        }
        
        // Guardamos los cambios    
        this.clubService.save(club);

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
            throw new ResourceNotFoundException("Club", "identificador", id);
        }

        // Eliminamos el logo asociado
        this.cloudinaryService.delete(club.getLogoId());

        // Eliminamos el club
        this.clubService.deleteById(id);

        return ResponseEntity.ok(ApiResponse
            .builder()
            .flag(true)
            .code(200)
            .message("Club eliminado correctamente")
            .data("No data provided")
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
