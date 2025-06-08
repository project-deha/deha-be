package org.deha.be.model.earthquakes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Earthquake extends BaseEarthquake {
    @Column(columnDefinition = "boolean default false")
    private boolean isAlarmed;
    @Column(unique = true, columnDefinition = "bigint default 0")
    private long eventID;
}
