package com.swm.ventybackend.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Post post) { em.persist(post); }

    public void remove(Long id) { em.remove(findById(id)); }

    public Post findById(Long id) { return em.find(Post.class, id); }

    public Post findByTitle(String findTitle) {
        return em.createQuery("SELECT post FROM Post post WHERE post.title = :findTitle", Post.class)
                .setParameter("findTitle", findTitle)
                .getSingleResult();
    }

    public List<Post> findAll() {
        return em.createQuery("SELECT post FROM Post post", Post.class)
                .getResultList();
    }
}
