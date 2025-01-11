package me.utku.dehabe.repository;

import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface PredictedEarthquakeRepository extends JpaRepository<PredictedEarthquake, UUID> {
    Page<PredictedEarthquake> findAllByMagnitudeIsBetweenOrLocation_CityOrAndPredictionDateIsBetween(
            float minMagnitude, float maxMagnitude,
            String city,
            Instant starDate, Instant startDate,
            Pageable pageable);
}