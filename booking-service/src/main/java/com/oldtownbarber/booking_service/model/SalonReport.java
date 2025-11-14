package com.oldtownbarber.booking_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Reporte detallado de estadísticas y métricas de un salón")
public class SalonReport {

    @Schema(description = "ID del salón", example = "1")
    private Long salonId;

    @Schema(description = "Nombre del salón", example = "Old Town Barber Shop")
    private String salonName;

    @Schema(description = "Total de ganancias del salón", example = "150000")
    private int totalEarnings;

    @Schema(description = "Número total de reservas realizadas", example = "45")
    private Integer totalBookings;

    @Schema(description = "Número de reservas canceladas", example = "5")
    private Integer canceledBookings;

    @Schema(description = "Total de reembolsos por reservas canceladas", example = "15000.0")
    private Double totalRefund;
}