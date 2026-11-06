package com.TaskBoard.Mapper;

import org.springframework.stereotype.Component;

import com.TaskBoard.DTO.BoardRequestDTO;
import com.TaskBoard.DTO.BoardResponseDTO;
import com.TaskBoard.Model.Board;

@Component
public class BoardMapper {

    /**
     * Convertit un DTO de création/mise à jour en Entité JPA.
     */
    public Board toEntity(BoardRequestDTO dto) {
        Board board = new Board();
        board.setTitle(dto.getTitle());
        return board;
    }

    /**
     * Convertit une Entité JPA en DTO de réponse.
     */
    public BoardResponseDTO toResponseDTO(Board board) {
        return BoardResponseDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .createdAt(board.getCreatedAt())
                .ownerId(board.getOwner() != null ? board.getOwner().getId() : null)
                .build();
    }
}

