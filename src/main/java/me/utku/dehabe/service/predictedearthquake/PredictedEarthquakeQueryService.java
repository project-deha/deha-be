package me.utku.dehabe.service.predictedearthquake;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.mapper.PredictedEarthquakeMapper;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.repository.PredictedEarthquakeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictedEarthquakeQueryService {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;
    private final PredictedEarthquakeMapper predictedEarthquakeMapper;

    public PredictedEarthquakeQueryService(PredictedEarthquakeRepository predictedEarthquakeRepository, PredictedEarthquakeMapper predictedEarthquakeMapper) {
        this.predictedEarthquakeRepository = predictedEarthquakeRepository;
        this.predictedEarthquakeMapper = predictedEarthquakeMapper;
    }

    public List<PredictedEarthquakeDto> getAll() {
        List<PredictedEarthquake> predictions = predictedEarthquakeRepository.findAll();
        return predictions.stream().map(predictedEarthquakeMapper::toDto).toList();
    }

    public List<PredictedEarthquakeDto> getAllFiltered(PredictedEarthquakeFilterDto filterDto) {
        Page<PredictedEarthquake> predictions = predictedEarthquakeRepository
                .findAllByMagnitudeIsBetweenAndDepthIsBetweenAndLocation_CityAndPossibilityIsBetween(
                        filterDto.minMagnitude(), filterDto.maxMagnitude(),
                        filterDto.minDepth(), filterDto.maxDepth(),
                        filterDto.city(),
                        filterDto.minPossibility(), filterDto.maxPossibility(),
                        PageRequest.of(filterDto.page(), filterDto.size())
                );
        return predictions.getContent().stream().map(predictedEarthquakeMapper::toDto).toList();
    }
}
