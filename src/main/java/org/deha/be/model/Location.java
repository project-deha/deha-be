package org.deha.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.enums.TurkishCity;
import org.deha.be.generic.BaseEntity;

@Entity
@Table(indexes = @Index(columnList = "city"))
@Getter
@Setter
public class Location extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private TurkishCity city;
    private float latitude;
    private float longitude;
}
