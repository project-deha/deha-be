package me.utku.dehabe.service.predictedearthquake;

import lombok.RequiredArgsConstructor;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeDto;
import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.mapper.PredictedEarthquakeMapper;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.repository.PredictedEarthquakeRepository;
import me.utku.dehabe.service.predictedearthquake.query.PredictedEarthquakesFilterQuery;
import me.utku.dehabe.service.predictedearthquake.query.PredictedEarthquakesMostPossiblesQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictedEarthquakeQueryService {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;
    private final PredictedEarthquakeMapper predictedEarthquakeMapper;
    private final PredictedEarthquakesFilterQuery filterPredictedEarthquakeQuery;
    private final PredictedEarthquakesMostPossiblesQuery predictedEarthquakesMostPossiblesQuery;

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
     * @return List of most possible earthquake predictions for each location. One prediction for per location.
     */
    public List<PredictedEarthquakeDto> getMostPossibles() {
        List<PredictedEarthquake> mostPossiblePredictedEarthquakes = predictedEarthquakesMostPossiblesQuery.query();
        return mostPossiblePredictedEarthquakes.stream().map(predictedEarthquakeMapper::toDto).toList();
    }
}
