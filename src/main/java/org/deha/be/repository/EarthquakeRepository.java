package org.deha.be.repository;

import org.deha.be.model.earthquakes.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EarthquakeRepository extends JpaRepository<Earthquake, UUID>, JpaSpecificationExecutor<Earthquake> {
    @Query(value = """
            SELECT 
                TO_TIMESTAMP(CONCAT(EXTRACT(YEAR FROM occurrence_date), '-', EXTRACT(MONTH FROM occurrence_date), '-01'), 'YYYY-MM-DD') AS date,
                AVG(magnitude) AS avgMagnitude,
                COUNT(*) AS count
            FROM earthquake
            WHERE EXTRACT(YEAR FROM occurrence_date) BETWEEN :startYear AND :endYear
            GROUP BY date
            ORDER BY date
            """, nativeQuery = true)
    List<Object[]> findMonthlyStats(@Param("startYear") int startYear,
                                    @Param("endYear") int endYear);
}