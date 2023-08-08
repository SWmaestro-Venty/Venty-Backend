package com.swm.ventybackend.collection;

import com.swm.ventybackend.users.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Collection collection) { em.persist(collection); }

    public void remove(Long id) { em.remove(findById(id)); }

    public Collection findById(Long id) { return em.find(Collection.class, id); }

    public Collection findByName(String findName) {
        return em.createQuery("SELECT collection FROM Collection collection WHERE collection.collectionName = :findName",
                Collection.class)
                .setParameter("findName", findName)
                .getSingleResult();
    }

    public List<Collection> findByUsersId(Users users) {
        return em.createQuery("SELECT collection FROM Collection collection WHERE collection.users = :users", Collection.class)
                .setParameter("users", users)
                .getResultList();
    }

    public List<Collection> findAll() {
        return em.createQuery("SELECT collection FROM Collection collection", Collection.class)
                .getResultList();
    }
}
