package org.deha.be.service.domain.earthquake.query;

import org.deha.be.dto.earthquake.EarthquakeFilterDto;
import org.deha.be.generic.query.ParameterizedQuery;
import org.deha.be.generic.query.baseearthquake.BaseEarthquakeFilterQuery;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EarthquakesFilterQuery extends BaseEarthquakeFilterQuery<Earthquake> implements ParameterizedQuery<Page<Earthquake>, EarthquakeFilterDto, Earthquake> {
    private final EarthquakeRepository earthquakeRepository;

    public EarthquakesFilterQuery(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
    }

    @Override
    public Page<Earthquake> query(EarthquakeFilterDto earthquakeFilter) {
        Specification<Earthquake> earthquakeSpecification = prepareSpecification(earthquakeFilter);
        return earthquakeRepository.findAll(earthquakeSpecification, PageRequest.of(earthquakeFilter.page(), earthquakeFilter.size()));
    }

    @Override
    public Specification<Earthquake> prepareSpecification(EarthquakeFilterDto earthquakeFilter) {
        return Specification.where(magnitudeBetween(earthquakeFilter.minMagnitude(), earthquakeFilter.maxMagnitude()))
                .and(cityEqual(earthquakeFilter.city()))
                .and(occurrenceDateBetween(earthquakeFilter.startDate(), earthquakeFilter.endDate()));
    }
}
