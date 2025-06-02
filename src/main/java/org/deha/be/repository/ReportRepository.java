package org.deha.be.repository;

import org.deha.be.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    List<Report> findAllByCreatedAtIsGreaterThanEqual(Instant date);
}
