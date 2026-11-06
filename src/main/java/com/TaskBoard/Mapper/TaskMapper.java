package com.TaskBoard.Mapper;

import org.springframework.stereotype.Component;

import com.TaskBoard.DTO.TaskRequestDTO;
import com.TaskBoard.DTO.TaskResponseDTO;
import com.TaskBoard.Model.Task;

@Component
public class TaskMapper {

    /**
     * Convertit un DTO de création/mise à jour en Entité JPA.
     */
    public Task toEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        return task;
    }

    /**
     * Convertit une Entité JPA en DTO de réponse.
     */
    public TaskResponseDTO toResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .boardId(task.getBoard() != null ? task.getBoard().getId() : null)
                .build();
    }
}

