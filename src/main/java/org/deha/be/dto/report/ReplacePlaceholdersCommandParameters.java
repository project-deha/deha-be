package org.deha.be.dto.report;

import org.deha.be.enums.TurkishCity;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.util.List;

public record ReplacePlaceholdersCommandParameters(
        WordprocessingMLPackage wordMLPackage,
        List<PredictedEarthquake> earthquakes,
        TurkishCity city,
        String month
) {
}
