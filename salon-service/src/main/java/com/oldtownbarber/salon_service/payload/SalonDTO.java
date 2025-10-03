package com.oldtownbarber.salon_service.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para la información de un salón de barbería")
public class SalonDTO {

    @Schema(description = "ID único del salón", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre del salón es obligatorio")
    @Schema(description = "Nombre del salón", example = "Old Town Barber Shop")
    private String name;

    @Schema(description = "Lista de URLs de imágenes del salón", example = "[\"https://example.com/image1.jpg\"]")
    private List<String> images;

    @NotBlank(message = "La dirección es obligatoria")
    @Schema(description = "Dirección física del salón", example = "Calle 123 #45-67")
    private String address;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Schema(description = "Número de teléfono de contacto", example = "+57 300 123 4567")
    private String phoneNumber;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    @Schema(description = "Correo electrónico del salón", example = "contact@oldtownbarber.com")
    private String email;

    @NotBlank(message = "La ciudad es obligatoria")
    @Schema(description = "Ciudad donde se encuentra el salón", example = "Bogotá")
    private String city;

    @Schema(description = "ID del propietario del salón", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long ownerId;

    @NotNull(message = "La hora de apertura es obligatoria")
    @Schema(description = "Hora de apertura del salón", example = "08:00:00")
    private LocalTime openTime;

    @NotNull(message = "La hora de cierre es obligatoria")
    @Schema(description = "Hora de cierre del salón", example = "20:00:00")
    private LocalTime closeTime;
}