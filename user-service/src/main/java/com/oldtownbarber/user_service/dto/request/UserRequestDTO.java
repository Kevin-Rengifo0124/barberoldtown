package com.oldtownbarber.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String fullName;

    @NotBlank(message = "Nombre de usuario obligatorio")
    private String userName;

    @NotBlank(message = "Correo electrónico obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotBlank(message = "Clave obligatoria")
    private String password;

    private String phone;

    @NotBlank(message = "Rol obligatorio")
    private String role;
}
