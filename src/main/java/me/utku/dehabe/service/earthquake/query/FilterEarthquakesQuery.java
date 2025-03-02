package me.utku.dehabe.service.earthquake.query;

import me.utku.dehabe.dto.earthquake.EarthquakeFilterDto;
import me.utku.dehabe.generic.Query;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.repository.EarthquakeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FilterEarthquakesQuery implements Query<Page<Earthquake>, EarthquakeFilterDto> {
    private final EarthquakeRepository earthquakeRepository;

    public FilterEarthquakesQuery(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
    }

    public Page<Earthquake> query(EarthquakeFilterDto earthquakeFilter) {
        Specification<Earthquake> earthquakeSpecification = prepareSpecification(earthquakeFilter);
        return earthquakeRepository.findAll(earthquakeSpecification, PageRequest.of(earthquakeFilter.page(), earthquakeFilter.size()));
    }

    private Specification<Earthquake> prepareSpecification(EarthquakeFilterDto earthquakeFilter) {
        return Specification.where(magnitudeBetween(earthquakeFilter.minMagnitude(), earthquakeFilter.maxMagnitude()))
                .and(cityEqual(earthquakeFilter.city()))
                .and(occurredDateBetween(earthquakeFilter.startDate(), earthquakeFilter.endDate()));
    }

    private Specification<Earthquake> magnitudeBetween(float minMagnitude, float maxMagnitude) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("magnitude"), minMagnitude, maxMagnitude);
    }

    private Specification<Earthquake> cityEqual(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("location.city"), city);
    }

    private Specification<Earthquake> occurredDateBetween(Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("occurredDate"), startDate, endDate);
    }
}
