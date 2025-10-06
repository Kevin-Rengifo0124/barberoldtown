package com.oldtownbarber.category_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Schema(description = "Entidad que representa una categoría de servicios")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Nombre de la categoría", example = "Cortes de cabello", required = true)
    private String name;

    @Schema(description = "URL de la imagen de la categoría", example = "https://example.com/category-image.jpg")
    private String image;

    @NotNull(message = "El ID del salón es obligatorio")
    @Column(nullable = false)
    @Schema(description = "ID del salón al que pertenece la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long salonId;
}