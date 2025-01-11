package me.utku.dehabe.repository;

import me.utku.dehabe.model.earthquakes.PredictedEarthquake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PredictedEarthquakeRepository extends JpaRepository<PredictedEarthquake, UUID> {
    Page<PredictedEarthquake> findAllByMagnitudeIsBetweenAndLocation_CityInAndPredictionDateIsBetween(
            float minMagnitude, float maxMagnitude,
            List<String> city,
            Instant starDate, Instant startDate,
            Pageable pageable);

    @Query("""
            SELECT pe 
            FROM PredictedEarthquake pe 
            WHERE pe.possibility = (
                SELECT MAX(innerPe.possibility) 
                FROM PredictedEarthquake innerPe 
                WHERE innerPe.location = pe.location
            )""")
    List<PredictedEarthquake> findMostPossibleEarthquakesForEachLocation();
}