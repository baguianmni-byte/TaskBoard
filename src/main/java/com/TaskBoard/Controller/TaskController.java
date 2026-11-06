package com.TaskBoard.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskBoard.DTO.TaskRequestDTO;
import com.TaskBoard.DTO.TaskResponseDTO;
import com.TaskBoard.Service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boards/{boardId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // CREATE

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable UUID boardId,
            @Valid @RequestBody TaskRequestDTO requestDTO) {

        TaskResponseDTO created = taskService.createTask(boardId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasks(@PathVariable UUID boardId) {
        return ResponseEntity.ok(taskService.getTasksByBoard(boardId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable UUID boardId,
            @PathVariable UUID taskId) {

        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    // UPDATE

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable UUID boardId,
            @PathVariable UUID taskId,
            @Valid @RequestBody TaskRequestDTO requestDTO) {

        return ResponseEntity.ok(taskService.updateTask(taskId, requestDTO));
    }

    // DELETE

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID boardId,
            @PathVariable UUID taskId) {

        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
