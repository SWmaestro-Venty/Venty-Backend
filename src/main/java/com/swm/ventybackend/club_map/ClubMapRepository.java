package com.swm.ventybackend.club_map;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(ClubMap clubMap) { em.persist(clubMap); }

    public void remove(Long id) { em.remove(findById(id)); }

    public ClubMap findById(Long id) { return em.find(ClubMap.class, id); }

    public List<ClubMap> findByUsersId(Long usersId) {
        return em.createQuery("SELECT clubMap FROM ClubMap clubMap WHERE clubMap.usersId = :usersId" , ClubMap.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

    public List<ClubMap> findByClubId(Long clubId) {
        return em.createQuery("SELECT clubMap FROM ClubMap clubMap WHERE clubMap.clubId = :clubId" , ClubMap.class)
                .setParameter("clubId", clubId)
                .getResultList();
    }

    public List<ClubMap> findAll() {
        return em.createQuery("SELECT clubMap FROM ClubMap clubMap", ClubMap.class)
                .getResultList();
    }

    public Long getClubMapCountByClubId(Long clubId) {
        return (Long) em.createQuery("SELECT COUNT(clubMap) FROM ClubMap clubMap WHERE clubMap.clubId =: clubId")
                .setParameter("clubId", clubId)
                .getSingleResult();
    }

    public Boolean isUsersExistClubByUsersIdAndClubId(Long usersId, Long clubId) {
        List<ClubMap> clubMaps = em.createQuery("SELECT clubMap FROM ClubMap clubMap WHERE clubMap.usersId =: usersId AND clubMap.clubId =: clubId", ClubMap.class)
                .setParameter("usersId", usersId)
                .setParameter("clubId", clubId)
                .getResultList();

        if (clubMaps.isEmpty())
            return false; // 중복 검사 통과

        return true;

    }
}
