package org.deha.be.service.domain.report;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.report.ReportDto;
import org.deha.be.mapper.ReportMapper;
import org.deha.be.model.Report;
import org.deha.be.repository.ReportRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportQueryService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public List<ReportDto> getReports() {
        Month currentMonth = LocalDateTime.now().getMonth();
        Instant startDate = LocalDate.of(LocalDateTime.now().getYear(), currentMonth, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        List<Report> reports = reportRepository.findAllByCreatedAtIsGreaterThanEqual(startDate);
        return reports.stream().map(reportMapper::toDto).toList();
    }

    public ResponseEntity<Resource> downloadById(UUID id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + id));
        Path path = Paths.get(report.getFilePath());

        if (!Files.exists(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dosya bulunamadı");
        }

        try {
            Resource resource = new UrlResource(path.toUri());
            String filename = report.getFilePath();
            String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + filename.replaceAll("[^\\x20-\\x7E]", "_") +
                    "\"; filename*=UTF-8''" + encodedFilename;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (
                MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Dosya erişim hatası", e);
        }


    }
}
