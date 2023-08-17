package com.swm.ventybackend.users;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class UsersRepository {

    private final EntityManager em;

    public void save(Users users) {
        em.persist(users);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public Users findById(Long id) {
        return em.find(Users.class, id);
    }

    public Users findByName(String findName) {
        return em.createQuery("SELECT users FROM Users users WHERE users.usersName = :findName", Users.class)
                .setParameter("findName", findName)
                .getSingleResult();
    }

    public Users findByEmail(String email) {
        return em.createQuery("SELECT users FROM Users users WHERE users.email = :email", Users.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public List<Users> findAll() {
        return em.createQuery("SELECT users FROM Users users", Users.class)
                .getResultList();
    }
}
