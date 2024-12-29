package me.utku.dehabe.model.earthquakes;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PredictedEarthquake extends BaseEarthquake {
    private float possibility;
}
