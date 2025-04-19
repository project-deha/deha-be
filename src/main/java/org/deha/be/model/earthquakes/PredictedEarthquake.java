package org.deha.be.model.earthquakes;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {@Index(columnList = "magnitude")})
public class PredictedEarthquake extends BaseEarthquake {

}
