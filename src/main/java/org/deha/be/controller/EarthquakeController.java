package org.deha.be.controller;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.EarthquakeDto;
import org.deha.be.dto.earthquake.EarthquakeFilterDto;
import org.deha.be.service.domain.earthquake.EarthquakeQueryService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/earthquake")
@RequiredArgsConstructor
public class EarthquakeController {
    private final EarthquakeQueryService earthquakeQueryService;

    @GetMapping("/filter")
    @PreAuthorize("hasRole('RESEARCHER')")
    public Page<EarthquakeDto> getFiltered(@RequestParam EarthquakeFilterDto earthquakeFilter) {
        return earthquakeQueryService.getFiltered(earthquakeFilter);
    }
}
