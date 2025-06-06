package org.deha.be.service.domain.report.command.table;

import org.deha.be.dto.report.SetTableCommandParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.docx4j.wml.Tbl;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@Service
public class SetMaxMinPredictionTableCommand extends BaseTableCommand implements EffectCommand<SetTableCommandParameters> {
    @Override
    public void execute(SetTableCommandParameters parameters) {
        PredictedEarthquake biggestMagnitudeEarthquake = parameters.earthquakes().stream()
                .reduce((first, second) -> first.getMagnitude() > second.getMagnitude() ? first : second)
                .orElse(null);

        PredictedEarthquake lowestMagnitudeEarthquake = parameters.earthquakes().stream()
                .reduce((first, second) -> first.getMagnitude() < second.getMagnitude() ? first : second)
                .orElse(null);

        String[][] maxData = {
                {formatter.format(biggestMagnitudeEarthquake.getOccurrenceDate().atZone(ZoneOffset.UTC)),
                        String.valueOf(biggestMagnitudeEarthquake.getMagnitude()),
                        String.valueOf(biggestMagnitudeEarthquake.getDepth()),
                }
        };

        String[][] minData = {
                {formatter.format(lowestMagnitudeEarthquake.getOccurrenceDate().atZone(ZoneOffset.UTC)),
                        String.valueOf(lowestMagnitudeEarthquake.getMagnitude()),
                        String.valueOf(lowestMagnitudeEarthquake.getDepth()),
                }
        };

        List<Tbl> tables = getDocxTables(parameters.documentPart());
        Tbl maxTable = tables.get(0);
        Tbl minTable = tables.get(1);

        setTableData(maxTable, maxData);
        setTableData(minTable, minData);
    }
}
