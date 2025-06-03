package org.deha.be.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.enums.TurkishCity;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.service.domain.predictedearthquake.query.PredictedEarthquakesFilterQuery;
import org.deha.be.service.domain.report.command.CreateMonthlyCityReportCommand;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateMonthlyReportTask {
    private static final long SCHEDULE_DELAY = 1000L * 60 * 60 * 24 * 30;
    private final PredictedEarthquakesFilterQuery predictedEarthquakesFilterQuery;
    private final CreateMonthlyCityReportCommand createMonthlyCityReportCommand;

    //@Scheduled(fixedDelay = SCHEDULE_DELAY)
    public void execute() {
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
