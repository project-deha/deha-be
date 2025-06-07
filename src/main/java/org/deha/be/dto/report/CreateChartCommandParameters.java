package org.deha.be.dto.report;

import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.util.List;

public record CreateChartCommandParameters(List<PredictedEarthquake> earthquakes,
                                           WordprocessingMLPackage wordMLPackage) {
}
