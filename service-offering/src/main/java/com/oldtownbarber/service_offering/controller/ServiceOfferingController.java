package com.oldtownbarber.service_offering.controller;

import com.oldtownbarber.service_offering.model.ServiceOffering;
import com.oldtownbarber.service_offering.service.ServiceOfferingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
@Tag(name = "Service Offerings", description = "API para consulta de servicios ofrecidos por los salones")
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    @Operation(summary = "Obtener servicios por salón",
            description = "Obtiene todos los servicios ofrecidos por un salón específico, con opción de filtrar por categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOffering.class))),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable
            @Parameter(description = "ID del salón", required = true) Long salonId,
            @RequestParam(required = false)
            @Parameter(description = "ID de la categoría para filtrar (opcional)") Long categoryId) {
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalon(salonId, categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID",
            description = "Obtiene un servicio específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOffering.class))),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado",
                    content = @Content)
    })
    public ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable
            @Parameter(description = "ID del servicio", required = true) Long id) throws Exception {
        ServiceOffering serviceOfferings = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/list/{ids}")
    @Operation(summary = "Obtener múltiples servicios por IDs",
            description = "Obtiene un conjunto de servicios a partir de una lista de IDs")
    @ApiResponse(responseCode = "200", description = "Servicios encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceOffering.class)))
    public ResponseEntity<Set<ServiceOffering>> getServiceByIds(
            @PathVariable
            @Parameter(description = "Lista de IDs de servicios separados por coma", required = true) Set<Long> ids) {
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServicesByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }
}