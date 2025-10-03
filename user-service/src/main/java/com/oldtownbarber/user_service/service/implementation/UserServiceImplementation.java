package com.oldtownbarber.user_service.service.implementation;

import com.oldtownbarber.user_service.dto.request.UserRequestDTO;
import com.oldtownbarber.user_service.dto.request.UserUpdateDTO;
import com.oldtownbarber.user_service.dto.response.UserResponseDTO;
import com.oldtownbarber.user_service.exception.UserException;
import com.oldtownbarber.user_service.mapper.UserMapper;
import com.oldtownbarber.user_service.model.User;
import com.oldtownbarber.user_service.repository.UserRepository;
import com.oldtownbarber.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        User savedUser = userRepository.save(user);
        //Convertimos el Entity en un RespnseDto
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return userMapper.toResponseDTO(optionalUser.get());
        }

        throw new UserException("Usuario no encontrado");
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseDTOList(users);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserException("Usuario no encontrado con id " + id);
        }

        User existingUser = optionalUser.get();
        userMapper.updateEntityFromDTO(existingUser, userUpdateDTO);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserException("Usuario no existe con id " + id);
        }

        userRepository.deleteById(optionalUser.get().getId());
    }
}