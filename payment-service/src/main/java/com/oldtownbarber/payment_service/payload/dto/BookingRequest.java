package com.oldtownbarber.payment_service.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(description = "DTO para solicitar la creación de una nueva reserva")
public class BookingRequest {

    @Schema(description = "Fecha y hora de inicio de la reserva", example = "2024-12-15T10:00:00", required = true)
    private LocalDateTime startTime;

    @Schema(description = "Fecha y hora de finalización de la reserva", example = "2024-12-15T11:00:00", required = true)
    private LocalDateTime endTime;

    @Schema(description = "Lista de IDs de servicios a reservar", example = "[1, 2, 3]", required = true)
    private Set<Long> serviceIds;
}