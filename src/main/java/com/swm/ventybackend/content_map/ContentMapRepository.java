package com.swm.ventybackend.content_map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ContentMapRepository{

    @PersistenceContext
    private final EntityManager em;

    public void save(ContentMap contentMap) { em.persist(contentMap); }

    public void remove(Long id) { em.remove(findById(id)); }

    public ContentMap findById(Long id) { return em.find(ContentMap.class, id); }

    public List<ContentMap> findAll() {
        return em.createQuery("SELECT contentMap FROM SubscribeMap contentMap", ContentMap.class)
                .getResultList();
    }

    public List<ContentMap> findAllContentIdByCollectionId(Long collectionId) {
        return em.createQuery("SELECT contentMap.contentId FROM ContentMap contentMap WHERE contentMap.collectionId =: collectionId", ContentMap.class)
                .setParameter("collectionId", collectionId)
                .getResultList();
    }
}
