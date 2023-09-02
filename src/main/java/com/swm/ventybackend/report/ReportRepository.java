package com.swm.ventybackend.report;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Report report) {
        em.persist(report);
    }

    public void remove(Long id) {
        em.remove(findByReportId(id));
    }

    public Report findByReportId(Long id) {
        return em.find(Report.class, id);
    }

    public List<Report> findReportsByUsersId(Long usersId) {
        return em.createQuery("SELECT report FROM Report report WHERE report.usersId =: usersId",
                Report.class)
                .setParameter("usersId", usersId)
                .getResultList();
    }

    public List<Report> findReportsByBlackId(Long blackId) {
        return em.createQuery("SELECT report FROM Report report WHERE report.blackId =: blackId",
                Report.class)
                .setParameter("blackId", blackId)
                .getResultList();
    }

    public List<Report> findReportsByBlackThingId(Long blackThingId) {
        return em.createQuery("SELECT report FROM Report report WHERE report.blackThingId =: blackThingId",
                Report.class)
                .setParameter("blackThingId", blackThingId)
                .getResultList();
    }
}
