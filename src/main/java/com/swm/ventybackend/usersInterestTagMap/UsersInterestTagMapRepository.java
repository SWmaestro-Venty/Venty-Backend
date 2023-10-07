package com.swm.ventybackend.usersInterestTagMap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsersInterestTagMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(UsersInterestTagMap usersInterestTagMap) {
        em.persist(usersInterestTagMap);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public UsersInterestTagMap findById(Long id) {
        return em.find(UsersInterestTagMap.class, id);
    }

    public List<UsersInterestTagMap> findUsersInterestTagMapsByUsersId(Long usersId) {
        return em.createQuery("SELECT usersInterestTagMap FROM UsersInterestTagMap usersInterestTagMap WHERE usersInterestTagMap.usersId =: usersId", UsersInterestTagMap.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

}
