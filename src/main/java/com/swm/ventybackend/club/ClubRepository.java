package com.swm.ventybackend.club;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubRepository {

    @PersistenceContext
    private final EntityManager em;


    public void save(Club club) { em.persist(club);}

    public void remove(Long id) { em.remove(findById(id)); }

    public Club findById(Long id) { return em.find(Club.class, id); }

    public Club findByName(String findName) {
        return em.createQuery("SELECT club FROM Club club WHERE club.clubName = :findName", Club.class)
                .setParameter("findName", findName)
                .getSingleResult();
    }

    public List<Club> findAll() {
        return em.createQuery("SELECT club FROM Club club", Club.class)
                .getResultList();
    }
}
