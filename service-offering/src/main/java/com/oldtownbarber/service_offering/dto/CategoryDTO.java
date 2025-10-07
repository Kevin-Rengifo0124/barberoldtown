package com.oldtownbarber.service_offering.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la información de una categoría de servicios")
public class CategoryDTO {

    @Schema(description = "ID único de la categoría", example = "1")
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Schema(description = "Nombre de la categoría", example = "Cortes de cabello")
    private String name;

    @Schema(description = "URL de la imagen de la categoría", example = "https://example.com/category-image.jpg")
    private String image;
}