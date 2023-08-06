package com.swm.ventybackend.content_rds;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentRepository {

    @PersistenceContext
    private final EntityManager em;


    public void save(Content content) { em.persist(content);}

    public void remove(Long id) { em.remove(findById(id)); }

    public Content findById(Long id) { return em.find(Content.class, id); }

//    public Content findByName(String findName) {
//        return em.createQuery("SELECT content FROM Content content WHERE content.contentName = :findName", Content.class)
//                .setParameter("findName", findName)
//                .getSingleResult();
//    }

    public List<Content> findAll() {
        return em.createQuery("SELECT content FROM Content content", Content.class)
                .getResultList();
    }
}
