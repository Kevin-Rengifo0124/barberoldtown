package com.oldtownbarber.service_offering.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la información de un servicio ofrecido por el salón")
public class ServiceDTO {

    @Schema(description = "ID único del servicio", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Schema(description = "Nombre del servicio", example = "Corte de cabello clásico")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Schema(description = "Descripción detallada del servicio", example = "Corte de cabello estilo clásico con acabado profesional")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Schema(description = "Precio del servicio en la moneda local", example = "25000")
    private int price;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    @Schema(description = "Duración del servicio en minutos", example = "30")
    private int duration;

    @Schema(description = "ID del salón que ofrece el servicio", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long salonId;

    @NotNull(message = "La categoría es obligatoria")
    @Schema(description = "ID de la categoría del servicio", example = "1")
    private Long categoryId;

    @Schema(description = "URL de la imagen del servicio", example = "https://example.com/service-image.jpg")
    private String image;
}