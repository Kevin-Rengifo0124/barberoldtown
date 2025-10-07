package com.oldtownbarber.service_offering.controller;

import com.oldtownbarber.service_offering.dto.CategoryDTO;
import com.oldtownbarber.service_offering.dto.SalonDTO;
import com.oldtownbarber.service_offering.dto.ServiceDTO;
import com.oldtownbarber.service_offering.model.ServiceOffering;
import com.oldtownbarber.service_offering.service.ServiceOfferingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
@Tag(name = "Salon Owner - Service Management", description = "API para la gestión de servicios por parte del propietario del salón")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    @Operation(summary = "Crear un nuevo servicio",
            description = "Crea un nuevo servicio para un salón específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOffering.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody @Valid
            @Parameter(description = "Datos del servicio a crear", required = true) ServiceDTO serviceDTO) {

        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering serviceOfferings = serviceOfferingService.createService(salonDTO, serviceDTO, categoryDTO);
        return ResponseEntity.ok(serviceOfferings);
    }

    @PatchMapping("/{serviceId}")
    @Operation(summary = "Actualizar un servicio existente",
            description = "Actualiza los datos de un servicio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOffering.class))),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable
            @Parameter(description = "ID del servicio a actualizar", required = true) Long serviceId,
            @RequestBody @Valid
            @Parameter(description = "Datos actualizados del servicio", required = true) ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOfferings = serviceOfferingService.updateService(serviceId, serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }
}