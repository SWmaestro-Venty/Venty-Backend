package com.swm.ventybackend.feed;

import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.feed.Feed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedRepository {
    
    @PersistenceContext
    private final EntityManager em;

    public void save(Feed feed) { em.persist(feed);}

    public void remove(Long id) { em.remove(findById(id)); }

    public Feed findById(Long id) { return em.find(Feed.class, id); }

    public Feed findByTitle(String findTitle) {
        return em.createQuery("SELECT feed FROM Feed feed WHERE feed.feedTitle = :findTitle", Feed.class)
                .setParameter("findTitle", findTitle)
                .getSingleResult();
    }

    public List<Feed> findAll() {
        return em.createQuery("SELECT feed FROM Feed feed", Feed.class)
                .getResultList();
    }

}
