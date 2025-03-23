package me.utku.dehabe.generic.query.baseearthquake;

import me.utku.dehabe.enums.TurkishCity;
import me.utku.dehabe.model.Location_;
import me.utku.dehabe.model.earthquakes.BaseEarthquake;
import me.utku.dehabe.model.earthquakes.BaseEarthquake_;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public abstract class BaseEarthquakeFilterQuery<T extends BaseEarthquake> {

    /**
     * @param minMagnitude minimum magnitude of the earthquake.
     * @param maxMagnitude maximum magnitude of the earthquake.
     * @return Specification to filter earthquakes by magnitude.
     */
    public Specification<T> magnitudeBetween(float minMagnitude, float maxMagnitude) {
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

    /**
     * @param city city of the earthquake.
     * @return Specification to filter earthquakes by city.
     */
    public Specification<T> cityEqual(TurkishCity city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.join(BaseEarthquake_.location).get(Location_.city), city);
        };
    }

    /**
     * @param startDate start date of the earthquake.
     * @param endDate   end date of the earthquake.
     * @return Specification to filter earthquakes by occurrence date.
     */
    public Specification<T> occurrenceDateBetween(Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            }
            if (startDate == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(BaseEarthquake_.occurrenceDate), endDate);
            }
            if (endDate == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEarthquake_.occurrenceDate), startDate);
            }
            return criteriaBuilder.between(root.get(BaseEarthquake_.occurrenceDate), startDate, endDate);
        };
    }
}
