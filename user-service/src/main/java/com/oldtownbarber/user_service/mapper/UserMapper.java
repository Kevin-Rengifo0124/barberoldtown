package com.oldtownbarber.user_service.mapper;

import com.oldtownbarber.user_service.dto.request.UserRequestDTO;
import com.oldtownbarber.user_service.dto.request.UserUpdateDTO;
import com.oldtownbarber.user_service.dto.response.UserResponseDTO;
import com.oldtownbarber.user_service.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setFullName(userRequestDTO.getFullName());
        user.setUserName(userRequestDTO.getUserName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setPhone(userRequestDTO.getPhone());
        user.setRole(userRequestDTO.getRole());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFullName(user.getFullName());
        userResponseDTO.setUserName(user.getUserName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdateAt(user.getUpdateAt());
        return userResponseDTO;
    }

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(User existingUser, UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getFullName() != null) {
            existingUser.setFullName(userUpdateDTO.getFullName());
        }
        if (userUpdateDTO.getUserName() != null) {
            existingUser.setUserName(userUpdateDTO.getUserName());
        }
        if (userUpdateDTO.getEmail() != null) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPhone() != null) {
            existingUser.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getRole() != null) {
            existingUser.setRole(userUpdateDTO.getRole());
        }
    }
}
