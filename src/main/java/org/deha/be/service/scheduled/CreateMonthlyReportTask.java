package org.deha.be.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.enums.TurkishCity;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.ReportRepository;
import org.deha.be.service.domain.predictedearthquake.query.PredictedEarthquakesFilterQuery;
import org.deha.be.service.domain.report.command.CreateMonthlyCityReportCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateMonthlyReportTask {
    private static final long SCHEDULE_DELAY = 1000L * 60 * 60 * 24 * 30;
    private final PredictedEarthquakesFilterQuery predictedEarthquakesFilterQuery;
    private final CreateMonthlyCityReportCommand createMonthlyCityReportCommand;
    private final ReportRepository reportRepository;
    @Value("${report.output_path}")
    private String reportOutputFolderPath;

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() {
        reportRepository.deleteAll();
        Path folderPath = Paths.get(reportOutputFolderPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    Files.delete(entry);
                    System.out.println("Deleted: " + entry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while deleting files in the report output folder: " + e.getMessage(), e);
        }
        Set<TurkishCity> cities = Set.of(TurkishCity.values());
        cities.forEach(city -> {
            Month currentMonth = LocalDateTime.now().getMonth();
            Instant startDate = LocalDate.of(LocalDateTime.now().getYear(), currentMonth, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant endDate = LocalDate.of(LocalDateTime.now().getYear(), currentMonth, currentMonth.length(false)).atStartOfDay().toInstant(ZoneOffset.UTC);
            PredictedEarthquakeFilterDto filterDto = new PredictedEarthquakeFilterDto(0f, 0f, city, startDate, endDate, 0, 31);
            Page<PredictedEarthquake> predictedEarthquakes = predictedEarthquakesFilterQuery.query(filterDto);
            createMonthlyCityReportCommand.execute(predictedEarthquakes.getContent());
        });
    }
}
