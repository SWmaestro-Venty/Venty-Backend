package com.swm.ventybackend.report;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public String create(@RequestParam Long blackId, String context, Long blackThingId, Long usersId) {
        Report report = new Report();
        report.setBlackId(blackId);
        report.setContext(context);
        report.setUsersId(usersId);
        report.setBlackThingId(blackThingId);

        Long reportId = reportService.saveReport(report);
        return reportId + "번 신고 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        reportService.removeReport(id);
        return id + "번 신고 삭제 완료";
    }

    @GetMapping("/findReportsByUsersId")
    public List<Report> findReportsByUsersId(Long usersId) {
        return reportService.findReportsByUsersId(usersId);
    }

    @GetMapping("/findReportsByBlackId")
    public List<Report> findReportsByBlackId(Long blackId) {
        return reportService.findReportsByBlackId(blackId);
    }

    @GetMapping("/findReportsByBlackThingId")
    public List<Report> findReportsByBlackThingId(Long blackThingId) {
        return reportService.findReportsByBlackThingId(blackThingId);
    }
}
