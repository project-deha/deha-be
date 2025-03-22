package me.utku.dehabe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import me.utku.dehabe.enums.TurkishCity;
import me.utku.dehabe.generic.BaseEntity;

@Entity
@Getter
@Setter
public class Location extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private TurkishCity city;
    private float latitude;
    private float longitude;
}
