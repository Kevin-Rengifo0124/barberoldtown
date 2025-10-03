package com.oldtownbarber.salon_service.controller;

import com.oldtownbarber.salon_service.mapper.SalonMapper;
import com.oldtownbarber.salon_service.model.Salon;
import com.oldtownbarber.salon_service.payload.SalonDTO;
import com.oldtownbarber.salon_service.payload.UserDTO;
import com.oldtownbarber.salon_service.service.SalonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
@Tag(name = "Salons", description = "API para la gestión de salones de barbería")
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    @Operation(summary = "Crear un nuevo salón", description = "Crea un nuevo salón en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salón creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<SalonDTO> createSalon(
            @RequestBody @Valid
            @Parameter(description = "Datos del salón a crear", required = true) SalonDTO requestDTO) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.createSalon(requestDTO, userDTO);
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar un salón", description = "Actualiza los datos de un salón existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salón actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable("id")
            @Parameter(description = "ID del salón", required = true) Long salonId,
            @RequestBody @Valid
            @Parameter(description = "Datos del salón a actualizar", required = true) SalonDTO requestDTO) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.updateSalon(requestDTO, userDTO, salonId);
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los salones", description = "Obtiene la lista de todos los salones registrados")
    @ApiResponse(responseCode = "200", description = "Lista de salones obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonDTO.class)))
    public ResponseEntity<List<SalonDTO>> getSalons() {
        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOS = salons.stream()
                .map(SalonMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    @Operation(summary = "Obtener salón por ID", description = "Obtiene un salón específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salón encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonDTO.class))),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<SalonDTO> getSalonById(
            @PathVariable
            @Parameter(description = "ID del salón", required = true) Long salonId) throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.getSalonById(salonId);
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar salones por ciudad", description = "Busca salones por nombre de ciudad, nombre del salón o dirección")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonDTO.class)))
    public ResponseEntity<List<SalonDTO>> searchSalons(
            @RequestParam("city")
            @Parameter(description = "Término de búsqueda (ciudad, nombre o dirección)", required = true) String city) {
        List<Salon> salons = salonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOS = salons.stream()
                .map(SalonMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner")
    @Operation(summary = "Obtener salón por ID del propietario", description = "Obtiene el salón asociado a un propietario específico")
    @ApiResponse(responseCode = "200", description = "Salón encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SalonDTO.class)))
    public ResponseEntity<SalonDTO> getSalonByOwnerId(@PathVariable Long salonId) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);

        Salon salon = salonService.getSalonByOwnerId(userDTO.getId());
        SalonDTO responseDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(responseDTO);
    }
}