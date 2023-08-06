package com.swm.ventybackend.comment;

import com.swm.ventybackend.comment.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Comment comment) { em.persist(comment);}

    public void remove(Long id) { em.remove(findById(id)); }

    public Comment findById(Long id) { return em.find(Comment.class, id); }

//    public Comment findByName(String findName) {
//        return em.createQuery("SELECT comment FROM Comment comment WHERE comment.commentName = :findName", Comment.class)
//                .setParameter("findName", findName)
//                .getSingleResult();
//    }

    public List<Comment> findAll() {
        return em.createQuery("SELECT comment FROM Comment comment", Comment.class)
                .getResultList();
    }
}
