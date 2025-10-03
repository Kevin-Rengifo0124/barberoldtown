package com.oldtownbarber.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String userName;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

