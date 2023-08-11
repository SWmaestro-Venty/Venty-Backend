package com.swm.ventybackend.board;

import com.swm.ventybackend.club.Club;
import com.swm.ventybackend.club.ClubService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public String create(@RequestParam String name, Integer category, Long clubId) {
        Board board = new Board();
        board.setName(name);
        board.setCategory(category);
        board.setClubId(clubId);

        Long boardId = boardService.saveBoard(board);
        return boardId + "번 보드 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        boardService.removeBoard(id);
        return id + "번 보드 삭제 완료";
    }

    @GetMapping("/findByIdOrName")
    public String read(@RequestParam @Nullable Long id, String name) {
        if(id != null) {
            return boardService.findBoardById(id).toString();
        } else {
            return boardService.findBoardByName(name).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() { return boardService.findAllBoard().toString(); }

}
