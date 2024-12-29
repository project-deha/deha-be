package me.utku.dehabe.controller;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.service.predictedearthquake.PredictedEarthquakeQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/filter")
    public List<PredictedEarthquakeDto> getAllFiltered(@RequestBody PredictedEarthquakeFilterDto filterDto) {
        return predictedEarthquakeQueryService.getAllFiltered(filterDto);
    }
}
