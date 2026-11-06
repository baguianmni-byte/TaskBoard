package com.TaskBoard.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TaskBoard.DTO.BoardRequestDTO;
import com.TaskBoard.DTO.BoardResponseDTO;
import com.TaskBoard.Exception.ResourceNotFoundException;
import com.TaskBoard.Mapper.BoardMapper;
import com.TaskBoard.Model.Board;
import com.TaskBoard.Model.User;
import com.TaskBoard.Repository.BoardRepository;

import lombok.RequiredArgsConstructor;

/**
 * Couche Service : contient la LOGIQUE METIER pour Board.
 */
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final BoardMapper boardMapper;

    // CREATE

    public BoardResponseDTO createBoard(UUID ownerId, BoardRequestDTO requestDTO) {
        User owner = userService.getUserEntityById(ownerId);
        Board board = boardMapper.toEntity(requestDTO);
        board.setOwner(owner);

        Board savedBoard = boardRepository.save(board);
        return boardMapper.toResponseDTO(savedBoard);
    }

    // READ

    public List<BoardResponseDTO> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(boardMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BoardResponseDTO> getBoardsByOwner(UUID ownerId) {
        userService.getUserEntityById(ownerId);
        return boardRepository.findByOwnerId(ownerId)
                .stream()
                .map(boardMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BoardResponseDTO getBoardById(UUID id) {
        Board board = getBoardEntityById(id);
        return boardMapper.toResponseDTO(board);
    }

    public Board getBoardEntityById(UUID id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tableau introuvable avec id=" + id));
    }

    // UPDATE

    public BoardResponseDTO updateBoard(UUID id, BoardRequestDTO requestDTO) {
        Board existingBoard = getBoardEntityById(id);
        existingBoard.setTitle(requestDTO.getTitle());

        Board updatedBoard = boardRepository.save(existingBoard);
        return boardMapper.toResponseDTO(updatedBoard);
    }

    // DELETE

    public void deleteBoard(UUID id) {
        if (!boardRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tableau introuvable avec id=" + id);
        }
        boardRepository.deleteById(id);
    }
}
