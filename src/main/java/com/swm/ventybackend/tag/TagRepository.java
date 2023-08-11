package com.swm.ventybackend.tag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Tag tag) { em.persist(tag); }

    public void remove(Long id) { em.remove(findById(id)); }

    public Tag findById(Long id) { return em.find(Tag.class, id); }

    public Tag findByName(String name) {
        return em.createQuery("SELECT tag FROM Tag tag WHERE tag.name =:name", Tag.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Tag> findAll() {
        return em.createQuery("SELECT tag FROM Tag tag", Tag.class)
                .getResultList();
    }
}
