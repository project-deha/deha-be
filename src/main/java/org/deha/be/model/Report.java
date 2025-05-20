package org.deha.be.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.enums.TurkishCity;
import org.deha.be.generic.BaseEntity;

import java.time.YearMonth;

@Entity
@Getter
@Setter
public class Report extends BaseEntity {
    private String title;
    private TurkishCity city;
    private YearMonth date;
    private String filePath;
}
