package org.deha.be.dto.report;

import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.util.List;

public record SetTableCommandParameters(List<PredictedEarthquake> earthquakes,
                                        MainDocumentPart documentPart) {
}
