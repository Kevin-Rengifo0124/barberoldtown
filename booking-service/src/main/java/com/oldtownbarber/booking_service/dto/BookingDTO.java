package com.oldtownbarber.booking_service.dto;

import com.oldtownbarber.booking_service.domain.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(description = "DTO que representa una reserva de servicios")
public class BookingDTO {

    @Schema(description = "ID único de la reserva", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID del salón donde se realiza la reserva", example = "1")
    private Long salondId;

    @Schema(description = "ID del cliente que realiza la reserva", example = "1")
    private Long customerId;

    @Schema(description = "Fecha y hora de inicio de la reserva", example = "2024-12-15T10:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Fecha y hora de finalización de la reserva", example = "2024-12-15T11:00:00")
    private LocalDateTime endTime;

    @Schema(description = "Lista de IDs de servicios incluidos en la reserva", example = "[1, 2, 3]")
    private Set<Long> serviceIds;

    @Schema(description = "Estado actual de la reserva", example = "PENDING", defaultValue = "PENDING")
    private BookingStatus status = BookingStatus.PENDING;
}