package org.deha.be.controller;

import org.deha.be.dto.earthquake.EarthquakeCityDistributionStatsDto;
import org.deha.be.dto.earthquake.EarthquakeMagnitudeDistributionStatsDto;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsDto;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsQueryParameters;
import org.deha.be.dto.predictedearthquake.MonthlyPredictedEarthquakeStatsQueryParameters;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeDto;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.service.domain.predictedearthquake.PredictedEarthquakeQueryService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/predicted-earthquake")
public class PredictedEarthquakeController {
    private final PredictedEarthquakeQueryService predictedEarthquakeQueryService;

    public PredictedEarthquakeController(PredictedEarthquakeQueryService predictedEarthquakeQueryService) {
        this.predictedEarthquakeQueryService = predictedEarthquakeQueryService;
    }

    @GetMapping
    public List<PredictedEarthquakeDto> getAll() {
        return predictedEarthquakeQueryService.getAll();
    }

    @GetMapping("/most-severe")
    public List<PredictedEarthquakeDto> getMostSeverePredictions() {
        return predictedEarthquakeQueryService.getMostSeverePredictions();
    }

    @GetMapping("/stats/monthly")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<MonthlyEarthquakeStatsDto> getMonthlyStats(@ModelAttribute MonthlyPredictedEarthquakeStatsQueryParameters params) {
        return predictedEarthquakeQueryService.getMonthlyStats(params);
    }

    @GetMapping("/stats/magnitude-distribution")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<EarthquakeMagnitudeDistributionStatsDto> getMagnitudeDistributionStats() {
        return predictedEarthquakeQueryService.getMagnitudeDistributionStats();
    }

    @GetMapping("/stats/city-distribution")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<EarthquakeCityDistributionStatsDto> getCityDistributionStats() {
        return predictedEarthquakeQueryService.getCityDistributionStats();
    }

    @PostMapping("/filter")
    public Page<PredictedEarthquakeDto> getFiltered(@RequestBody PredictedEarthquakeFilterDto predictedEarthquakeFilter) {
        return predictedEarthquakeQueryService.getFiltered(predictedEarthquakeFilter);
    }
}
