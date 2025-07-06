package org.deha.be.service.domain.predictedearthquake;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.EarthquakeCityDistributionStatsDto;
import org.deha.be.dto.earthquake.EarthquakeMagnitudeDistributionStatsDto;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsDto;
import org.deha.be.dto.predictedearthquake.MonthlyPredictedEarthquakeStatsQueryParameters;
import org.deha.be.dto.predictedearthquake.MostSeverePredictedEarthquakeFilterParameters;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeDto;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.enums.TurkishCity;
import org.deha.be.mapper.PredictedEarthquakeMapper;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.PredictedEarthquakeRepository;
import org.deha.be.service.domain.predictedearthquake.query.PredictedEarthquakesFilterQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PredictedEarthquakeQueryService {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;
    private final PredictedEarthquakeMapper predictedEarthquakeMapper;
    private final PredictedEarthquakesFilterQuery filterPredictedEarthquakeQuery;

    public List<PredictedEarthquakeDto> getAll() {
        List<PredictedEarthquake> predictions = predictedEarthquakeRepository.findAll();
        return predictions.stream().map(predictedEarthquakeMapper::toDto).toList();
    }

    /**
     * @param predictedEarthquakeFilter Filter object for filtering predictions.
     * @return Paginated filtered earthquake predictions.
     */
    public Page<PredictedEarthquakeDto> getFiltered(PredictedEarthquakeFilterDto predictedEarthquakeFilter) {
        Page<PredictedEarthquake> predictedEarthquakes = filterPredictedEarthquakeQuery.query(predictedEarthquakeFilter);
        return predictedEarthquakes.map(predictedEarthquakeMapper::toDto);
    }

    /**
     * @return List of most severe earthquake predictions for each location. One prediction for per location.
     */
    public List<PredictedEarthquakeDto> getMostSeverePredictions(MostSeverePredictedEarthquakeFilterParameters parameters) {
        List<PredictedEarthquake> mostSeverePredictedEarthquakes = predictedEarthquakeRepository.findMostSeverePredictedEarthquakesByCityFiltered(
                Optional.ofNullable(parameters.startDate()).orElse(Instant.EPOCH),
                Optional.ofNullable(parameters.endDate()).orElse(LocalDateTime.of(2025, 12, 31, 0, 0, 0).toInstant(ZoneOffset.UTC)),
                CollectionUtils.isEmpty(parameters.city()) ? TurkishCity.getValues() : parameters.city().stream().map(TurkishCity::getValue).toList(),
                parameters.minMagnitude(),
                parameters.maxMagnitude()
        );
        return mostSeverePredictedEarthquakes.stream().map(predictedEarthquakeMapper::toDto).toList();
    }

    public List<MonthlyEarthquakeStatsDto> getMonthlyStats(MonthlyPredictedEarthquakeStatsQueryParameters params) {
        return predictedEarthquakeRepository.findMonthlyStats(params.startDate(), params.endDate());
    }

    public List<EarthquakeMagnitudeDistributionStatsDto> getMagnitudeDistributionStats() {
        return predictedEarthquakeRepository.getPredictedEarthquakeMagnitudeStatistics();
    }

    public List<EarthquakeCityDistributionStatsDto> getCityDistributionStats() {
        return predictedEarthquakeRepository.getPredictedEarthquakeCityStats();
    }
}
