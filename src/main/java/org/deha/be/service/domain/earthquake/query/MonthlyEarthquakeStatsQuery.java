package org.deha.be.service.domain.earthquake.query;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsQueryParameters;
import org.deha.be.generic.query.ParameterizedQuery;
import org.deha.be.model.earthquakes.Earthquake;
import org.deha.be.repository.EarthquakeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyEarthquakeStatsQuery implements ParameterizedQuery<List<Earthquake>, MonthlyEarthquakeStatsQueryParameters, Earthquake> {
    private final EarthquakeRepository earthquakeRepository;

    @Override
    public List<Earthquake> query(MonthlyEarthquakeStatsQueryParameters monthlyEarthquakeStatsQueryParameters) {
        Specification<Earthquake> specification = prepareSpecification(monthlyEarthquakeStatsQueryParameters);
        return earthquakeRepository.findAll(specification);
    }

    @Override
    public Specification<Earthquake> prepareSpecification(MonthlyEarthquakeStatsQueryParameters monthlyEarthquakeStatsQueryParameters) {
        return (root, query, criteriaBuilder) -> {
            return null;
        };
    }
}
