package com.swm.ventybackend.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Board board) { em.persist(board); }

    public void remove(Long id) { em.remove(findById(id)); }

    public Board findById(Long id) { return em.find(Board.class, id); }

    public Board findByName(String findName) {
        return em.createQuery("SELECT board FROM Board board WHERE board.name = :findName",
                Board.class)
                .setParameter("findName", findName)
                .getSingleResult();
    }

    public List<Board> findAll() {
        return em.createQuery("SELECT board From Board board", Board.class)
                .getResultList();
    }
}
