package com.swm.ventybackend.privateClubDetail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrivateClubDetailRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(PrivateClubDetail privateClubDetail) {
        em.persist(privateClubDetail);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public  PrivateClubDetail findById (Long id) {
        return em.find(PrivateClubDetail.class, id);
    }

    public List<PrivateClubDetail> findPrivateClubDetailsByUsersId(Long usersId) {
        return em.createQuery("SELECT pcd FROM PrivateClubDetail pcd WHERE pcd.usersId =: usersId", PrivateClubDetail.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

    public PrivateClubDetail findPrivateClubDetailByClubId(Long clubId) {
        return em.createQuery("SELECT pcd FROM PrivateClubDetail pcd WHERE pcd.clubId =: clubId", PrivateClubDetail.class)
                .setParameter("clubId", clubId)
                .getSingleResult();
    }

//    public void updatePrivateClubPasswordByClubId(Long clubId, String password) {
//        em.createQuery("UPDATE PrivateClubDetail pcd SET pcd.")
//    }

    public void updatePrivateClubMaxUsersByClubId(Long clubId, Integer maxUsers) {
        em.createQuery("UPDATE PrivateClubDetail pcd SET pcd.privateClubMaxUsers =: maxUsers WHERE pcd.clubId =: clubId")
                .setParameter("maxUsers", maxUsers)
                .setParameter("clubId", clubId)
                .executeUpdate();
        em.flush();
    }

    public void updatePrivateClubOwnerByClubId(Long clubId, Long usersId) {
        em.createQuery("UPDATE PrivateClubDetail pcd SET pcd.usersId =: usersId WHERE pcd.clubId =: clubId")
                .setParameter("usersId", usersId)
                .setParameter("clubId", clubId)
                .executeUpdate();
        em.flush();
    }
}


