package com.oldtownbarber.user_service.service;

import com.oldtownbarber.user_service.dto.request.UserRequestDTO;
import com.oldtownbarber.user_service.dto.request.UserUpdateDTO;
import com.oldtownbarber.user_service.dto.response.UserResponseDTO;
import com.oldtownbarber.user_service.exception.UserException;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id) throws UserException;

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws UserException;

    void deleteUser(Long id) throws UserException;

}