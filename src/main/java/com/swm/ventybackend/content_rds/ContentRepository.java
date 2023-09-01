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

    public List<Content> findContentsByUsersId(Long usersId) {
        return em.createQuery("SELECT content FROM Content content WHERE content.usersId =:usersId", Content.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

    public List<Content> findAll() {
        return em.createQuery("SELECT content FROM Content content", Content.class)
                .getResultList();
    }

    public List<Content> getTenContents() {
        return em.createQuery("SELECT content FROM Content content", Content.class)
                .setMaxResults(10)
                .getResultList();
    }

    public Integer getDownloadCountByContentId(Long contentId) {
        return em.createQuery("SELECT content.downloadCount FROM Content content WHERE content.contentId =: contentId", Integer.class)
                .setParameter("contentId", contentId)
                .getSingleResult();
    }
}
