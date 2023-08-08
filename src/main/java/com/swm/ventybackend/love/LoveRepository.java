package com.swm.ventybackend.love;

import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoveRepository {

    @PersistenceContext
    private final EntityManager em;

    private final UsersService usersService;

    public void save(Love love) { em.persist(love); }

    public void remove(Long id) { em.remove(findById(id)); }

    public Love findById(Long id) { return em.find(Love.class, id); }

    public Love findByUsersId(Long findUserId) {
        Users users = usersService.findUsersById(findUserId);

        return em.createQuery("SELECT love FROM Love love WHERE love.users =:users", Love.class)
                .setParameter("findUserId", findUserId)
                .getSingleResult();
    }

    public List<Love> findAll() {
        return em.createQuery("SELECT love FROM Love love", Love.class)
                .getResultList();
    }
}
