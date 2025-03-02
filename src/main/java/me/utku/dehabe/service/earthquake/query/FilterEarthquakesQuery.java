package me.utku.dehabe.service.earthquake.query;

import me.utku.dehabe.dto.earthquake.EarthquakeFilterDto;
import me.utku.dehabe.generic.Query;
import me.utku.dehabe.model.Location_;
import me.utku.dehabe.model.earthquakes.BaseEarthquake_;
import me.utku.dehabe.model.earthquakes.Earthquake;
import me.utku.dehabe.model.earthquakes.Earthquake_;
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
        return (root, query, criteriaBuilder) -> {
            if (minMagnitude == 0 && maxMagnitude == 0) {
                return criteriaBuilder.conjunction();
            }
            if (minMagnitude == 0) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(BaseEarthquake_.magnitude), maxMagnitude);
            }
            if (maxMagnitude == 0) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEarthquake_.magnitude), minMagnitude);
            }
            return criteriaBuilder.between(root.get(BaseEarthquake_.magnitude), minMagnitude, maxMagnitude);
        };
    }

    private Specification<Earthquake> cityEqual(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(BaseEarthquake_.location).get(Location_.city), city);
        };
    }

    private Specification<Earthquake> occurredDateBetween(Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            }
            if (startDate == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(Earthquake_.occurredDate), endDate);
            }
            if (endDate == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(Earthquake_.occurredDate), startDate);
            }
            return criteriaBuilder.between(root.get(Earthquake_.occurredDate), startDate, endDate);
        };
    }
}
