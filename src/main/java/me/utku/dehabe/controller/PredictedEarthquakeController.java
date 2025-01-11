package me.utku.dehabe.controller;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.service.predictedearthquake.PredictedEarthquakeQueryService;
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

    @GetMapping("/most-possible")
    public List<PredictedEarthquakeDto> getAllFiltered() {
        return predictedEarthquakeQueryService.getMostPossibles();
    }

    @PostMapping("/filter")
    public Page<PredictedEarthquake> getAllFiltered(@RequestBody PredictedEarthquakeFilterDto filterDto) {
        return predictedEarthquakeQueryService.getAllFiltered(filterDto);
    }
}
