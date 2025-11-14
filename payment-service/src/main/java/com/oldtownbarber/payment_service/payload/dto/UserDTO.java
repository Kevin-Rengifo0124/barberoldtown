package com.oldtownbarber.payment_service.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para información básica de usuario")
public class UserDTO {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
    private String email;
}