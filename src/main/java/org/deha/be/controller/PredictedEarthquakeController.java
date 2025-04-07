package org.deha.be.controller;

import org.deha.be.dto.predictedearthquake.PredictedEarthquakeDto;
import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.service.domain.predictedearthquake.PredictedEarthquakeQueryService;
import org.springframework.data.domain.Page;
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

    @PostMapping("/filter")
    public Page<PredictedEarthquakeDto> getFiltered(@RequestBody PredictedEarthquakeFilterDto predictedEarthquakeFilter) {
        return predictedEarthquakeQueryService.getFiltered(predictedEarthquakeFilter);
    }
}
