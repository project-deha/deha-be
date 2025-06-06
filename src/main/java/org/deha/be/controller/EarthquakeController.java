package org.deha.be.controller;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.*;
import org.deha.be.service.domain.earthquake.EarthquakeQueryService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/earthquake")
@RequiredArgsConstructor
public class EarthquakeController {
    private final EarthquakeQueryService earthquakeQueryService;

    @GetMapping("/most-severe")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<EarthquakeDto> getMostSevereEarthquakes(@ModelAttribute MostSevereEarthquakeFilterParameters parameters) {
        return earthquakeQueryService.getMostSevereEarthquakes(parameters);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('RESEARCHER')")
    public Page<EarthquakeDto> getFiltered(@ModelAttribute EarthquakeFilterDto earthquakeFilter) {
        return earthquakeQueryService.getFiltered(earthquakeFilter);
    }

    @GetMapping("/stats/monthly")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<MonthlyEarthquakeStatsDto> getMonthlyStats(@ModelAttribute MonthlyEarthquakeStatsQueryParameters params) {
        return earthquakeQueryService.getMonthlyStats(params);
    }

    @GetMapping("/stats/magnitude-distribution")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<EarthquakeMagnitudeDistributionStatsDto> getMagnitudeDistributionStats() {
        return earthquakeQueryService.getMagnitudeDistributionStats();
    }

    @GetMapping("/stats/city-distribution")
    @PreAuthorize("hasRole('RESEARCHER')")
    public List<EarthquakeCityDistributionStatsDto> getCityDistributionStats() {
        return earthquakeQueryService.getCityDistributionStats();
    }
}
