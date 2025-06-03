package org.deha.be.service.domain.earthquake;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.*;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeDto;
import org.deha.be.mapper.EarthquakeMapper;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.deha.be.service.domain.earthquake.query.EarthquakesFilterQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
        return earthquakeRepository.findMonthlyStats(params.startYear(), params.endYear());
    }

    public List<EarthquakeMagnitudeDistributionStatsDto> getMagnitudeDistributionStats() {
        return earthquakeRepository.getEarthquakeMagnitudeStatistics();
    }

    public List<EarthquakeCityDistributionStatsDto> getCityDistributionStats() {
        return earthquakeRepository.getEarthquakeCityStats();
    }

    public List<EarthquakeDto> getMostSevereEarthquakes() {
        List<Earthquake> mostSeverePredictedEarthquakes = earthquakeRepository.findMostSevereEarthquakesByCity();
        return mostSeverePredictedEarthquakes.stream().map(earthquakeMapper::toDto).toList();
    }
}
