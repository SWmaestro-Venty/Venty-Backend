package com.swm.ventybackend.tagMap;

import com.swm.ventybackend.content_rds.Content;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(TagMap tagMap) {
        em.persist(tagMap);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public TagMap findById(Long id) {
        return em.find(TagMap.class, id);
    }

    public List<TagMap> findAll() {
        return em.createQuery("SELECT tagMap FROM TagMap tagMap", TagMap.class)
                .getResultList();
    }

    public List<TagMap> findTagMapsByContentId(Long contentId) {
        return em.createQuery("SELECT tagMap FROM TagMap tagMap WHERE tagMap.contentId =: contentId", TagMap.class)
                .setParameter("contentId", contentId)
                .getResultList();
    }


    // saveTagMapByTagNameList

//    public List<Content> findContentByManyTagName(List<String> tagNameList) {
//
//    }
}
