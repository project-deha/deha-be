package me.utku.dehabe.model.earthquakes;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import me.utku.dehabe.generic.BaseEntity;
import me.utku.dehabe.model.Location;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEarthquake extends BaseEntity {
    private float magnitude;
    private float depth;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
