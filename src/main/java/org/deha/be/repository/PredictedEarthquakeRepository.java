package org.deha.be.repository;

import org.deha.be.dto.earthquake.EarthquakeCityDistributionStatsDto;
import org.deha.be.dto.earthquake.EarthquakeMagnitudeDistributionStatsDto;
import org.deha.be.dto.earthquake.MonthlyEarthquakeStatsDto;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PredictedEarthquakeRepository extends JpaRepository<PredictedEarthquake, UUID>, JpaSpecificationExecutor<PredictedEarthquake> {
    @Query(value = """
            SELECT
                MAKE_DATE(EXTRACT(YEAR FROM occurrence_date)::int, EXTRACT(MONTH FROM occurrence_date)::int, 1) AS date,
                AVG(magnitude) AS avgMagnitude,
                COUNT(*) AS count
            FROM earthquake
            WHERE occurrence_date BETWEEN :startDate AND :endDate
            GROUP BY MAKE_DATE(EXTRACT(YEAR FROM occurrence_date)::int, EXTRACT(MONTH FROM occurrence_date)::int, 1)
            ORDER BY date
            """, nativeQuery = true)
    List<MonthlyEarthquakeStatsDto> findMonthlyStats(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);


    @Query(value =
            "SELECT " +
                    "CASE " +
                    "  WHEN magnitude >= 2.0 AND magnitude < 3.0 THEN '2.0-2.9' " +
                    "  WHEN magnitude >= 3.0 AND magnitude < 4.0 THEN '3.0-3.9' " +
                    "  WHEN magnitude >= 4.0 AND magnitude < 5.0 THEN '4.0-4.9' " +
                    "  WHEN magnitude >= 5.0 AND magnitude < 6.0 THEN '5.0-5.9' " +
                    "  WHEN magnitude >= 6.0 AND magnitude < 7.0 THEN '6.0-6.9' " +
                    "  WHEN magnitude >= 7.0 THEN '7.0+' " +
                    "END as range, " +
                    "COUNT(*) as count, " +
                    "ROUND(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER(), 2) as percentage " +
                    "FROM predicted_earthquake " +
                    "GROUP BY CASE " +
                    "  WHEN magnitude >= 2.0 AND magnitude < 3.0 THEN '2.0-2.9' " +
                    "  WHEN magnitude >= 3.0 AND magnitude < 4.0 THEN '3.0-3.9' " +
                    "  WHEN magnitude >= 4.0 AND magnitude < 5.0 THEN '4.0-4.9' " +
                    "  WHEN magnitude >= 5.0 AND magnitude < 6.0 THEN '5.0-5.9' " +
                    "  WHEN magnitude >= 6.0 AND magnitude < 7.0 THEN '6.0-6.9' " +
                    "  WHEN magnitude >= 7.0 THEN '7.0+' " +
                    "END " +
                    "ORDER BY range",
            nativeQuery = true)
    List<EarthquakeMagnitudeDistributionStatsDto> getEarthquakeMagnitudeStatistics();

    @Query(value =
            "WITH city_counts AS ( " +
                    "SELECT l.city, COUNT(e.id) AS cnt " +
                    "FROM predicted_earthquake e " +
                    "JOIN location l ON e.location_id = l.id " +
                    "WHERE e.magnitude > 3 " +
                    "GROUP BY l.city " +
                    "), " +
                    "ranked_cities AS ( " +
                    "SELECT city, cnt, DENSE_RANK() OVER (ORDER BY cnt DESC) AS city_rank " +
                    "FROM city_counts " +
                    "), " +
                    "grouped_cities AS ( " +
                    "SELECT " +
                    "  CASE WHEN city_rank <= 5 THEN city ELSE 'Other' END AS city, " +
                    "  SUM(cnt) AS count " +
                    "FROM ranked_cities " +
                    "GROUP BY CASE WHEN city_rank <= 5 THEN city ELSE 'Other' END " +
                    ") " +
                    "SELECT " +
                    "  g.city AS city, " +
                    "  g.count AS count, " +
                    "  (g.count * 100.0 / (SELECT SUM(count) FROM grouped_cities)) AS percentage " +
                    "FROM grouped_cities g " +
                    "ORDER BY CASE WHEN g.city = 'Other' THEN 1 ELSE 0 END, g.count DESC",
            nativeQuery = true)
    List<EarthquakeCityDistributionStatsDto> getEarthquakeCityStats();

    @Query(value = """
            WITH RankedEarthquakes AS (
                SELECT e.*,
                       ROW_NUMBER() OVER (PARTITION BY l.city ORDER BY e.magnitude DESC) as rn
                FROM predicted_earthquake e
                JOIN location l ON e.location_id = l.id
                WHERE e.occurrence_date BETWEEN :startDate AND :endDate
                AND l.city IN (:cities)
                AND e.magnitude BETWEEN :minMagnitude AND :maxMagnitude
            )
            SELECT * FROM RankedEarthquakes
            WHERE rn = 1
            ORDER BY magnitude DESC
            """, nativeQuery = true)
    List<PredictedEarthquake> findMostSeverePredictedEarthquakesByCityFiltered(
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            @Param("cities") List<String> cities,
            @Param("minMagnitude") float minMagnitude,
            @Param("maxMagnitude") float maxMagnitude
    );
}