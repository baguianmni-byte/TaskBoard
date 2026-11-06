package com.TaskBoard.DTO;

import java.util.UUID;

import com.TaskBoard.Model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private UUID boardId;
}

