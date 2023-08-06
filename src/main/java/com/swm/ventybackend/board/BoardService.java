package com.swm.ventybackend.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long saveBoard(Board board) {
        boardRepository.save(board);
        return board.getBoardId();
    }

    public void removeBoard(Long id) { boardRepository.remove(id); }

    public Board findBoardById(Long id) { return boardRepository.findById(id); }

    public Board findBoardByName(String name) { return boardRepository.findByName(name); }

    public List<Board> findAllBoard() { return boardRepository.findAll(); }

}
