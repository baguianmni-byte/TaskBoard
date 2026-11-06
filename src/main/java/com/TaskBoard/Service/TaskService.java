package com.TaskBoard.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TaskBoard.DTO.TaskRequestDTO;
import com.TaskBoard.DTO.TaskResponseDTO;
import com.TaskBoard.Exception.ResourceNotFoundException;
import com.TaskBoard.Mapper.TaskMapper;
import com.TaskBoard.Model.Board;
import com.TaskBoard.Model.Task;
import com.TaskBoard.Model.TaskStatus;
import com.TaskBoard.Repository.TaskRepository;

import lombok.RequiredArgsConstructor;

/**
 * Couche Service : contient la LOGIQUE METIER pour Task.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final BoardService boardService;
    private final TaskMapper taskMapper;

    // CREATE

    public TaskResponseDTO createTask(UUID boardId, TaskRequestDTO requestDTO) {
        Board board = boardService.getBoardEntityById(boardId);

        Task task = taskMapper.toEntity(requestDTO);
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.A_FAIRE);
        }
        task.setBoard(board);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(savedTask);
    }

    // READ

    public List<TaskResponseDTO> getTasksByBoard(UUID boardId) {
        boardService.getBoardEntityById(boardId);
        return taskRepository.findByBoardId(boardId)
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskById(UUID id) {
        Task task = getTaskEntityById(id);
        return taskMapper.toResponseDTO(task);
    }

    public Task getTaskEntityById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache introuvable avec id=" + id));
    }

    // UPDATE

    public TaskResponseDTO updateTask(UUID id, TaskRequestDTO requestDTO) {
        Task existingTask = getTaskEntityById(id);

        existingTask.setTitle(requestDTO.getTitle());
        existingTask.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) {
            existingTask.setStatus(requestDTO.getStatus());
        }

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toResponseDTO(updatedTask);
    }

    // DELETE

    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tache introuvable avec id=" + id);
        }
        taskRepository.deleteById(id);
    }
}
