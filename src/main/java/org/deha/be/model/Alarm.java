package org.deha.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.enums.TurkishCity;
import org.deha.be.generic.BaseEntity;

@Entity
@Table(indexes = @Index(columnList = "minimumMagnitude,maximumMagnitude"))
@Getter
@Setter
public class Alarm extends BaseEntity {
    @ManyToOne
    private User owner;
    private float minimumMagnitude;
    private float maximumMagnitude;
    @Enumerated(EnumType.STRING)
    private TurkishCity city;
    private boolean isActive;
}
