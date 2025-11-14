package com.oldtownbarber.booking_service.model;

import com.oldtownbarber.booking_service.domain.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
@Schema(description = "Entidad que representa una reserva de servicios en un salón")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la reserva", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID del salón donde se realiza la reserva", example = "1", required = true)
    private Long salonId;

    @Schema(description = "ID del cliente que realiza la reserva", example = "1", required = true)
    private Long customerId;

    @Schema(description = "Fecha y hora de inicio de la reserva", example = "2024-12-15T10:00:00", required = true)
    private LocalDateTime startTime;

    @Schema(description = "Fecha y hora de finalización de la reserva", example = "2024-12-15T11:00:00", required = true)
    private LocalDateTime endTime;

    @ElementCollection
    @Schema(description = "Lista de IDs de servicios incluidos en la reserva", example = "[1, 2, 3]", required = true)
    private Set<Long> serviceIds;

    @Schema(description = "Estado actual de la reserva", example = "PENDING", defaultValue = "PENDING")
    private BookingStatus status = BookingStatus.PENDING;

    @Schema(description = "Precio total de todos los servicios en la reserva", example = "15000")
    private int totalPrices;
}