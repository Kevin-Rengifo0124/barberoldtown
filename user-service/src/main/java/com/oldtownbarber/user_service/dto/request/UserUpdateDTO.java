package com.oldtownbarber.user_service.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String fullName;
    private String userName;

    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    private String phone;
    private String role;
}
