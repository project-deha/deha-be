package me.utku.dehabe.service.predictedearthquake;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.enums.TurkishCity;
import me.utku.dehabe.mapper.PredictedEarthquakeMapper;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.repository.PredictedEarthquakeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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

    public Page<PredictedEarthquake> getAllFiltered(PredictedEarthquakeFilterDto filterDto) {
        return predictedEarthquakeRepository.findAllByMagnitudeIsBetweenAndLocation_CityInAndPredictionDateIsBetween(
                filterDto.minMagnitude() == 0F ? 1F : filterDto.minMagnitude(),
                filterDto.maxMagnitude() == 0F ? 10F : filterDto.maxMagnitude(),
                Optional.ofNullable(filterDto.city()).map(List::of).orElse(TurkishCity.getValues()),
                Optional.ofNullable(filterDto.startDate()).orElse(Instant.now()),
                Optional.ofNullable(filterDto.endDate()).orElse(Instant.now().plus(365, ChronoUnit.DAYS)),
                PageRequest.of(filterDto.page(), filterDto.size())
        );
    }

    public List<PredictedEarthquakeDto> getMostPossibles() {
        return predictedEarthquakeRepository.findMostPossibleEarthquakesForEachLocation().stream()
                .map(predictedEarthquakeMapper::toDto)
                .toList();
    }
}
