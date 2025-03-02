package me.utku.dehabe.model.earthquakes;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Earthquake extends BaseEarthquake {
    private Instant occurredDate;
}
