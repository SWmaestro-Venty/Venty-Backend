package com.swm.ventybackend.tagMap;

import com.swm.ventybackend.content_rds.Content;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    public List<Long> findContentIdsByTagNames(List<String> tagNames) {
        long tagCount = tagNames.size();

        String jpql = "SELECT tm.contentId FROM TagMap tm WHERE tm.tagId IN " +
                "(SELECT t.tagId FROM Tag t WHERE t.name IN :tagNames) " +
                "GROUP BY tm.contentId " +
                "HAVING COUNT(tm.contentId) = :tagCount";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("tagNames", tagNames);
        query.setParameter("tagCount", tagCount);

        return query.getResultList();
    }
}
