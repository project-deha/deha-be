package org.deha.be.service.domain.earthquake;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.EarthquakeDto;
import org.deha.be.dto.earthquake.EarthquakeFilterDto;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsDto;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsQueryParameters;
import org.deha.be.mapper.EarthquakeMapper;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.deha.be.service.domain.earthquake.query.EarthquakesFilterQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthquakeQueryService {
    private final EarthquakesFilterQuery filterEarthquakesQuery;
    private final EarthquakeRepository earthquakeRepository;
    private final EarthquakeMapper earthquakeMapper;

    /**
     * @param earthquakeFilter filter object to filter earthquakes.
     * @return Paginated filtered earthquakes.
     */
    public Page<EarthquakeDto> getFiltered(EarthquakeFilterDto earthquakeFilter) {
        Page<Earthquake> earthquakes = filterEarthquakesQuery.query(earthquakeFilter);
        return earthquakes.map(earthquakeMapper::toDto);
    }

    public List<MonthlyEarthquakeStatsDto> getMonthlyStats(MonthlyEarthquakeStatsQueryParameters params) {
        List<Object[]> raw = earthquakeRepository.findMonthlyStats(params.startYear(), params.endYear());
        return raw.stream()
                .map(r -> new MonthlyEarthquakeStatsDto(
                        ((Instant) r[0]),
                        ((Number) r[1]).floatValue(),
                        ((Number) r[2]).intValue()))
                .toList();
    }
}
