package com.swm.ventybackend.clubBasicTagMap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubBasicTagMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(ClubBasicTagMap clubBasicTagMap) {
        em.persist(clubBasicTagMap);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public ClubBasicTagMap findById(Long id) {
        return em.find(ClubBasicTagMap.class, id);
    }

    public List<ClubBasicTagMap> findClubBasicTagMapsByClubId(Long clubId) {
        return em.createQuery("SELECT clubBasicTagMap FROM ClubBasicTagMap clubBasicTagMap WHERE clubBasicTagMap.clubId =: clubId", ClubBasicTagMap.class)
                .setParameter("clubId", clubId)
                .getResultList();
    }

}
