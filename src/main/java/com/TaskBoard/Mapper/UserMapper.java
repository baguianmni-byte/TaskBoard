package com.TaskBoard.Mapper;

import org.springframework.stereotype.Component;

import com.TaskBoard.DTO.UserRequestDTO;
import com.TaskBoard.DTO.UserResponseDTO;
import com.TaskBoard.Model.User;

@Component
public class UserMapper {

    /**
     * Convertit un DTO de création/mise à jour en Entité JPA.
     */
    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    /**
     * Convertit une Entité JPA en DTO de réponse (sans mot de passe).
     */
    public UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}