package com.swm.ventybackend.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User findByName(String findName) {
        return em.createQuery("SELECT user FROM User user WHERE user.nickName = :findName", User.class)
                .setParameter("findName", findName)
                .getSingleResult();
    }

    public List<User> findAll() {
        return em.createQuery("SELECT user FROM User user", User.class)
                .getResultList();
    }
}
