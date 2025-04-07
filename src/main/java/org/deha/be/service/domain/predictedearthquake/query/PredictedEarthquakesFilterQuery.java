package org.deha.be.service.domain.predictedearthquake.query;

import org.deha.be.dto.predictedearthquake.PredictedEarthquakeFilterDto;
import org.deha.be.generic.query.ParameterizedQuery;
import org.deha.be.generic.query.baseearthquake.BaseEarthquakeFilterQuery;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.PredictedEarthquakeRepository;
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
