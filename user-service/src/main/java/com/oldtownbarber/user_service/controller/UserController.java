package com.oldtownbarber.user_service.controller;

import com.oldtownbarber.user_service.dto.request.UserRequestDTO;
import com.oldtownbarber.user_service.dto.request.UserUpdateDTO;
import com.oldtownbarber.user_service.dto.response.UserResponseDTO;
import com.oldtownbarber.user_service.exception.UserException;
import com.oldtownbarber.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "API para la gestión de usuarios")
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid
            @Parameter(description = "Datos del usuario a crear", required = true) UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/api/users")
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene la lista de todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class)))
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/api/users/{userId}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content)
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable("userId")
            @Parameter(description = "ID del usuario", required = true) Long id) throws UserException {
        UserResponseDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content)
    })
    public ResponseEntity<UserResponseDTO> updateUser(
            @RequestBody @Valid
            @Parameter(description = "Datos del usuario a actualizar", required = true) UserUpdateDTO userUpdateDTO,
            @PathVariable
            @Parameter(description = "ID del usuario", required = true) Long id) throws UserException {
        UserResponseDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuario eliminado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content)
    })
    public ResponseEntity<String> deleteUserById(
            @PathVariable
            @Parameter(description = "ID del usuario", required = true) Long id) throws UserException {
        userService.deleteUser(id);
        return new ResponseEntity<>("Usuario eliminado", HttpStatus.ACCEPTED);
    }
}