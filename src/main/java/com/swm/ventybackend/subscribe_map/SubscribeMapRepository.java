package com.swm.ventybackend.subscribe_map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscribeMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(SubscribeMap subscribeMap) { em.persist(subscribeMap); }

    public void remove(Long id) { em.remove(findById(id)); }

    public SubscribeMap findById(Long id) { return em.find(SubscribeMap.class, id); }

    public List<SubscribeMap> findAll() {
        return em.createQuery("SELECT subScribeMap FROM SubscribeMap subScribeMap", SubscribeMap.class)
                .getResultList();
    }

    public List<SubscribeMap> findSubscribeMapsByUsersId(Long usersId) {
        return em.createQuery("SELECT subScribeMap FROM SubscribeMap subScribeMap WHERE subScribeMap.usersId =: usersId", SubscribeMap.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

    public Boolean checkExistSubscribeMapByUsersIdAndCollectionId(Long usersId, Long collectionId) {
        List<SubscribeMap> resultQuery = em.createQuery("SELECT subScribeMap FROM SubscribeMap subScribeMap WHERE subScribeMap.usersId =: usersId AND subScribeMap.collectionId =: collectionId", SubscribeMap.class)
                .setParameter("usersId", usersId)
                .setParameter("collectionId", collectionId)
                .getResultList();

        if (resultQuery.isEmpty())
            return false;

        else
            return true;

    }
}
