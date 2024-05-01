package com.club.service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.club.service.controllers.DTO.ClubDTO;
import com.club.service.controllers.DTO.ClubResponseDTO;
import com.club.service.controllers.Payload.ApiResponse;
import com.club.service.models.Club;
import com.club.service.services.CloudinaryService;
import com.club.service.services.ClubService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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
        
        Club club = this.clubService.findById(id);

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
        @RequestParam("file") MultipartFile file,
        @Valid ClubDTO clubDTO
    ){

        try {

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
        
        }catch (Exception e) {
           
            return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(true)
                .code(200)
                .message("Pokémon registrado correctamente")
                .data("No data provided")
                .build()
            );

        }

        return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(true)
                .code(200)
                .message("Pokémon registrado correctamente")
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
        @RequestParam("file") MultipartFile file,
        @Valid ClubDTO clubDTO
    ){

        try {

            // Consultar el club a actualizar
            Club club = this.clubService.findById(id);

            if (this.clubService.findByStadium(estadio)){}

            // Cargamos el archivo
            Map<String, String> map = this.cloudinaryService.uploadFile(file, nombre);

            
            
            this.clubService.save(club);
        
        }catch (Exception e) {
           
            return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(true)
                .code(200)
                .message("Pokémon registrado correctamente")
                .data("No data provided")
                .build()
            );

        }

        return ResponseEntity.ok(ApiResponse
                .builder()
                .flag(true)
                .code(200)
                .message("Pokémon registrado correctamente")
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
