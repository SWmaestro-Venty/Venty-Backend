package com.swm.ventybackend.block_map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlockMapRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(BlockMap blockMap) {
        em.persist(blockMap);
    }

    public void remove(Long id) {
        em.remove(findById(id));
    }

    public BlockMap findById(Long id) {
        return em.find(BlockMap.class, id);
    }

    public List<BlockMap> findByUsersId(Long id) {
        return em.createQuery("SELECT blockMap FROM BlockMap blockMap WHERE blockMap.usersId =: id",
                        BlockMap.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<BlockMap> findByBlockedUsersByUsersId(Long id) {
        return em.createQuery("SELECT blockMap FROM BlockMap blockMap WHERE blockMap.blockedUsersId =: id",
                        BlockMap.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void removeByBothId(Long usersId, Long blockedUsersId) {
        em.createQuery("DELETE FROM BlockMap blockMap WHERE blockMap.usersId =: usersId AND blockMap.blockedUsersId =: blockedUsersId",
                        BlockMap.class)
                .setParameter("usersId", usersId)
                .setParameter("blockedUsersId", blockedUsersId);
    }
}
