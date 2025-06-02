package org.deha.be.dto.report;

import org.deha.be.enums.TurkishCity;

import java.util.UUID;

public record ReportDto(UUID id, String title, TurkishCity city, String date) {
}
