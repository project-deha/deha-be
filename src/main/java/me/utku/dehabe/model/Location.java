package me.utku.dehabe.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import me.utku.dehabe.enums.TurkishCity;
import me.utku.dehabe.generic.BaseEntity;

@Entity
@Getter
@Setter
public class Location extends BaseEntity {
    private TurkishCity city;
    private float latitude;
    private float longitude;
}
