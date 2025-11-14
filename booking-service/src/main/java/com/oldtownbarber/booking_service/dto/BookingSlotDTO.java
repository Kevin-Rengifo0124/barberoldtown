package com.oldtownbarber.booking_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO que representa una franja horaria reservada")
public class BookingSlotDTO {

    @Schema(description = "Fecha y hora de inicio de la franja horaria", example = "2024-12-15T10:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Fecha y hora de finalización de la franja horaria", example = "2024-12-15T11:00:00")
    private LocalDateTime endTime;
}