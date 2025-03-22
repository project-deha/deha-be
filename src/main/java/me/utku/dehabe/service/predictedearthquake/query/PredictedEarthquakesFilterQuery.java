package me.utku.dehabe.service.predictedearthquake.query;

import me.utku.dehabe.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import me.utku.dehabe.generic.query.ParameterizedQuery;
import me.utku.dehabe.generic.query.baseearthquake.BaseEarthquakeFilterQuery;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.repository.PredictedEarthquakeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PredictedEarthquakesFilterQuery extends BaseEarthquakeFilterQuery<PredictedEarthquake> implements ParameterizedQuery<Page<PredictedEarthquake>, PredictedEarthquakeFilterDto, PredictedEarthquake> {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;

    public PredictedEarthquakesFilterQuery(PredictedEarthquakeRepository predictedEarthquakeRepository) {
        this.predictedEarthquakeRepository = predictedEarthquakeRepository;
    }

    @Override
    public Page<PredictedEarthquake> query(PredictedEarthquakeFilterDto predictedEarthquakeFilter) {
        Specification<PredictedEarthquake> predictedEarthquakeSpecification = prepareSpecification(predictedEarthquakeFilter);
        return predictedEarthquakeRepository.findAll(predictedEarthquakeSpecification, PageRequest.of(predictedEarthquakeFilter.page(), predictedEarthquakeFilter.size()));
    }

    @Override
    public Specification<PredictedEarthquake> prepareSpecification(PredictedEarthquakeFilterDto predictedEarthquakeFilter) {
        return Specification.where(magnitudeBetween(predictedEarthquakeFilter.minMagnitude(), predictedEarthquakeFilter.maxMagnitude()))
                .and(cityEqual(predictedEarthquakeFilter.city()))
                .and(occurrenceDateBetween(predictedEarthquakeFilter.startDate(), predictedEarthquakeFilter.endDate()));
    }
}
