package org.deha.be.controller;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.report.ReportDto;
import org.deha.be.service.domain.report.ReportQueryService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportQueryService reportQueryService;

    @GetMapping
    public List<ReportDto> getReports() {
        return reportQueryService.getReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getReportById(UUID id) {
        return reportQueryService.downloadById(id);
    }
}
