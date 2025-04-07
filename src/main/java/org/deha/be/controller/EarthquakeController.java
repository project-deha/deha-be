package org.deha.be.controller;

import org.deha.be.dto.earthquake.EarthquakeDto;
import org.deha.be.dto.earthquake.EarthquakeFilterDto;
import org.deha.be.service.domain.earthquake.EarthquakeQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/earthquake")
public class EarthquakeController {
    private final EarthquakeQueryService earthquakeQueryService;

    public EarthquakeController(EarthquakeQueryService earthquakeQueryService) {
        this.earthquakeQueryService = earthquakeQueryService;
    }

    @GetMapping("/filter")
    public Page<EarthquakeDto> getFiltered(@RequestParam EarthquakeFilterDto earthquakeFilter) {
        return earthquakeQueryService.getFiltered(earthquakeFilter);
    }
}
