package org.deha.be.service.domain.report.command.table;

import org.deha.be.dto.report.SetTableCommandParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.docx4j.wml.Tbl;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@Service
public class SetPredictionsTableCommand extends BaseTableCommand implements EffectCommand<SetTableCommandParameters> {
    @Override
    public void execute(SetTableCommandParameters parameters) {
        String[][] data = new String[parameters.earthquakes().size()][4];

        for (int i = 0; i < parameters.earthquakes().size(); i++) {
            PredictedEarthquake earthquake = parameters.earthquakes().get(i);
            data[i][0] = formatter.format(earthquake.getOccurrenceDate().atZone(ZoneOffset.UTC));
            data[i][1] = String.valueOf(earthquake.getMagnitude());
            data[i][2] = String.valueOf(earthquake.getDepth());
            data[i][3] = String.valueOf(earthquake.getLocation().getCity());
        }
        List<Tbl> tables = getDocxTables(parameters.documentPart());
        Tbl table = tables.get(3);
        setTableData(table, data);
    }
}
