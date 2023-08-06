package com.swm.ventybackend.comment;

import com.swm.ventybackend.comment.Comment;
import com.swm.ventybackend.comment.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long saveComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    public void removeComment(Long id) { commentRepository.remove(id); }

    public Comment findCommentById(Long id) { return commentRepository.findById(id); }

//    public Comment findCommentByName(String name) { return commentRepository.findByName(name); }

    public List<Comment> findAllComment() { return commentRepository.findAll(); }

}
