package com.swm.ventybackend.usersTerm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsersTermRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(UsersTerm usersTerm) {
        em.persist(usersTerm);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public UsersTerm findById(Long id) {
        return em.find(UsersTerm.class, id);
    }

    public List<UsersTerm> findUsersTermByUsersId(Long usersId) {
        return em.createQuery("SELECT usersTerm FROM UsersTerm usersTerm WHERE usersTerm.usersId = :usersId", UsersTerm.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }
}
