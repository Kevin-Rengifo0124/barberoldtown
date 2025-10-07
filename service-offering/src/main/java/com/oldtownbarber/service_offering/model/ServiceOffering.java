package com.oldtownbarber.service_offering.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "services")
@Schema(description = "Entidad que representa un servicio ofrecido por un salón")
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del servicio", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del servicio", example = "Corte de cabello clásico")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Descripción detallada del servicio", example = "Corte de cabello estilo clásico con acabado profesional")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Precio del servicio", example = "25000")
    private int price;

    @Column(nullable = false)
    @Schema(description = "Duración del servicio en minutos", example = "30")
    private int duration;

    @Column(nullable = false)
    @Schema(description = "ID del salón que ofrece el servicio", example = "1")
    private Long salonId;

    @Column(nullable = false)
    @Schema(description = "ID de la categoría del servicio", example = "1")
    private Long categoryId;

    @Schema(description = "URL de la imagen del servicio", example = "https://example.com/service-image.jpg")
    private String image;
}