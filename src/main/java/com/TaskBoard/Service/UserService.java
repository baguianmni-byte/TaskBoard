package com.TaskBoard.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskBoard.DTO.UserRequestDTO;
import com.TaskBoard.DTO.UserResponseDTO;
import com.TaskBoard.Exception.DuplicateResourceException;
import com.TaskBoard.Exception.ResourceNotFoundException;
import com.TaskBoard.Mapper.UserMapper;
import com.TaskBoard.Model.User;
import com.TaskBoard.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Couche Service : contient la LOGIQUE METIER.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    // CREATE

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Un utilisateur avec cet email existe deja : " + requestDTO.getEmail());
        }

        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    
    }

    // READ

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable avec id=" + id));
        return userMapper.toResponseDTO(user);
    
    }

    public User getUserEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable avec id=" + id));
    }

    // UPDATE

    public UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO) {

        // 1. Récupérer l'utilisateur existant (ou 404)
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur introuvable avec id=" + id));

        // 2. Vérifier l'email : seulement s'il a changé ET qu'il est déjà pris
        boolean emailHasChanged = !existingUser.getEmail().equals(requestDTO.getEmail());
        if (emailHasChanged && userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Un utilisateur avec cet email existe déjà : " + requestDTO.getEmail());
        }

        // 3. Appliquer les modifications
        existingUser.setName(requestDTO.getName());
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // 4. Sauvegarder
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);
    
    }

    // DELETE

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur introuvable avec id=" + id);
        }
        userRepository.deleteById(id);
    }
}