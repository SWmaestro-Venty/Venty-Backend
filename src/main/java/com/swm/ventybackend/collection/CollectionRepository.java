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

    public List<Collection> findByUsersId(Long usersId) {
        return em.createQuery("SELECT collection FROM Collection collection WHERE collection.usersId = :usersId", Collection.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

    public List<Collection> findAll() {
        return em.createQuery("SELECT collection FROM Collection collection", Collection.class)
                .getResultList();
    }

    public Collection updateThumbnail(Long collectionId, String thumbnailUrl) {
        Collection collection = em.find(Collection.class, collectionId);
        collection.setThumbnailImageUrl(thumbnailUrl);
        return collection;
    }

    public List<Collection> findRandomCollectionByClubIdAndAmount(Long clubId, Integer amount) {
        return em.createQuery("SELECT collection FROM Collection collection WHERE collection.clubId =: clubId ORDER BY RAND()")
                .setParameter("clubId", clubId)
                .setMaxResults(amount)
                .getResultList();
    }

    public List<Collection> findCollectionsByUsersIdAndClubId(Long usersId, Long clubId) {
        return em.createQuery("SELECT collection FROM Collection collection WHERE collection.usersId =: usersId AND collection.clubId =: clubId", Collection.class)
                .setParameter("usersId", usersId)
                .setParameter("clubId", clubId)
                .getResultList();
    }

    public Collection addOneCollectionLikeByCollectionId(Long collectionId) {
        Collection collection = em.find(Collection.class, collectionId);
        collection.setLikes(collection.getLikes() + 1);
        return collection;
    }

    public Collection minusOneCollectionLikeByCollectionId(Long collectionId) {
        Collection collection = em.find(Collection.class, collectionId);
        collection.setLikes(collection.getLikes() - 1);
        return collection;
    }

}
