package org.deha.be.model.earthquakes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.generic.BaseEntity;
import org.deha.be.model.Location;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEarthquake extends BaseEntity {
    private float magnitude;
    private float depth;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    private Instant occurrenceDate;
}
