package com.swm.ventybackend.cloud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CloudRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Cloud cloud) {
        em.persist(cloud);
    }

    public void removeCloud(Long id) {
        em.remove(findByCloudId(id));
    }

    public Cloud findByCloudId(Long id) {
        return em.find(Cloud.class, id);
    }

    public Cloud findCloudByUsersId(Long usersId) {
        return em.createQuery("SELECT cloud FROM Cloud cloud WHERE cloud.usersId =: usersId", Cloud.class)
                .setParameter("usersId", usersId)
                .getSingleResult();
    }

    public List<Cloud> findCloudsByStatus(Integer status) {
        return em.createQuery("SELECT cloud FROM Cloud cloud WHERE cloud.status =: status", Cloud.class)
                .setParameter("status", status)
                .getResultList();
    }
}
