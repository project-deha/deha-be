package org.deha.be.repository;

import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PredictedEarthquakeRepository extends JpaRepository<PredictedEarthquake, UUID>, JpaSpecificationExecutor<PredictedEarthquake> {
}