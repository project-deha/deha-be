package org.deha.be.repository;

import org.deha.be.model.earthquakes.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EarthquakeRepository extends JpaRepository<Earthquake, UUID>, JpaSpecificationExecutor<Earthquake> {
}