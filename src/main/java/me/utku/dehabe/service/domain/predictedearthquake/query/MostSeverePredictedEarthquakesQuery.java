package me.utku.dehabe.service.domain.predictedearthquake.query;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import me.utku.dehabe.generic.query.ParameterlessQuery;
import me.utku.dehabe.model.earthquakes.BaseEarthquake_;
import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import me.utku.dehabe.repository.PredictedEarthquakeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MostSeverePredictedEarthquakesQuery implements ParameterlessQuery<List<PredictedEarthquake>, PredictedEarthquake> {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;

    public MostSeverePredictedEarthquakesQuery(PredictedEarthquakeRepository predictedEarthquakeRepository) {
        this.predictedEarthquakeRepository = predictedEarthquakeRepository;
    }

    @Override
    public List<PredictedEarthquake> query() {
        Specification<PredictedEarthquake> mostPossibleEarthquakesSpecification = prepareSpecification();
        return predictedEarthquakeRepository.findAll(mostPossibleEarthquakesSpecification);
    }

    @Override
    public Specification<PredictedEarthquake> prepareSpecification() {
        return (root, query, criteriaBuilder) -> {
            Subquery<Float> maxPossibilitySubquery = query.subquery(Float.class);
            Root<PredictedEarthquake> subRoot = maxPossibilitySubquery.from(PredictedEarthquake.class);

            maxPossibilitySubquery
                    .select(criteriaBuilder.max(subRoot.get(BaseEarthquake_.magnitude)))
                    .where(criteriaBuilder.equal(subRoot.join(BaseEarthquake_.location), root.get(BaseEarthquake_.location)));

            Predicate mainPredicate = criteriaBuilder.equal(
                    root.get(BaseEarthquake_.magnitude),
                    maxPossibilitySubquery
            );

            query.distinct(true);
            query.orderBy(criteriaBuilder.desc(root.get(BaseEarthquake_.magnitude)));

            return mainPredicate;
        };
    }
}
