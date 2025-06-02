package org.deha.be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.enums.TurkishCity;
import org.deha.be.generic.BaseEntity;

@Entity
@Getter
@Setter
public class Report extends BaseEntity {
    private String title;
    @Enumerated(EnumType.STRING)
    private TurkishCity city;
    private String date;
    private String filePath;
}
