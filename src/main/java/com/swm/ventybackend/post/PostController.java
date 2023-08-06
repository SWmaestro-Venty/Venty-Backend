package com.swm.ventybackend.post;

import com.swm.ventybackend.board.Board;
import com.swm.ventybackend.board.BoardService;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final BoardService boardService;

    @PostMapping("/create")
    public String create(@RequestParam Integer category, String title, String context,
                         @Nullable Integer status, Long boardId) {
        Post post = new Post();
        post.setCategory(category);
        post.setTitle(title);
        post.setContext(context);
        if (status != null) post.setStatus(status);

        Board board = boardService.findBoardById(boardId);
        post.setBoard(board);

        Long postId = postService.savePost(post);
        return postId + "번 게시글 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        postService.removePost(id);
        return id + "번 게시글 삭제 완료";
    }

    @GetMapping("/findByIdOrTitle")
    public String read(@RequestParam @Nullable Long id, String title) {
        if(id != null) {
            return postService.findPostById(id).toString();
        } else {
            return postService.findPostByTitle(title).toString();
        }
    }

    @GetMapping("/all")
    public String readAll() { return postService.findAllPost().toString(); }
}
