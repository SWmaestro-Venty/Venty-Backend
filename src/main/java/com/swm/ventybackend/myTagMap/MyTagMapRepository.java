package com.swm.ventybackend.myTagMap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyTagMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(MyTagMap myTagMap) {
        em.persist(myTagMap);
    }

    public void remove(Long id){
        em.remove(findById(id));
    }

    public MyTagMap findById(Long id) {
        return em.find(MyTagMap.class, id);
    }

    public List<MyTagMap> findMyTagMapsByUsersId(Long usersId) {
        return em.createQuery("SELECT myTagMap FROM MyTagMap myTagMap WHERE myTagMap.usersId =: usersId", MyTagMap.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }
}
