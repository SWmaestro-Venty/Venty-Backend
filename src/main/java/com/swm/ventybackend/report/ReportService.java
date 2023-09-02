package com.swm.ventybackend.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public Long saveReport(Report report) {
        reportRepository.save(report);
        return report.getReportId();
    }

    public void removeReport(Long reportId) {
        reportRepository.remove(reportId);
    }

    public List<Report> findReportsByUsersId(Long usersId) {
        return reportRepository.findReportsByUsersId(usersId);
    }

    public List<Report> findReportsByBlackId(Long blackId) {
        return reportRepository.findReportsByBlackId(blackId);
    }

    public List<Report> findReportsByBlackThingId(Long blackThingId) {
        return reportRepository.findReportsByBlackThingId(blackThingId);
    }

}
