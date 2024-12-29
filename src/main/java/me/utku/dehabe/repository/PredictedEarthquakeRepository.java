package me.utku.dehabe.repository;

import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PredictedEarthquakeRepository extends JpaRepository<PredictedEarthquake, UUID> {
    Page<PredictedEarthquake> findAllByMagnitudeIsBetweenAndDepthIsBetweenAndLocation_CityAndPossibilityIsBetween(
            float minMagnitude, float maxMagnitude,
            float minDepth, float maxDepth,
            String city,
            float minPossibility, float maxPossibility,
            Pageable pageable);
}