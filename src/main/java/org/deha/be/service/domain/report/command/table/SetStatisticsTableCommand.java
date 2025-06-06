package org.deha.be.service.domain.report.command.table;

import org.deha.be.dto.report.SetTableCommandParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.earthquakes.BaseEarthquake;
import org.docx4j.wml.Tbl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetStatisticsTableCommand extends BaseTableCommand implements EffectCommand<SetTableCommandParameters> {
    @Override
    public void execute(SetTableCommandParameters parameters) {
        float sumMagnitude = parameters.earthquakes().stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(0F, Float::sum);

        float avgMagnitude = sumMagnitude / parameters.earthquakes().size();

        float variance = parameters.earthquakes().stream()
                .map(BaseEarthquake::getMagnitude)
                .map(magnitude -> (magnitude - avgMagnitude) * (magnitude - avgMagnitude))
                .reduce(0F, Float::sum) / (parameters.earthquakes().size() - 1);

        float standardDeviation = (float) Math.sqrt(variance);

        String[][] data = {{
                String.valueOf(sumMagnitude),
                String.valueOf(parameters.earthquakes().size()),
                String.valueOf(avgMagnitude),
                String.valueOf(standardDeviation),
                String.valueOf(variance),
        }};
        List<Tbl> tables = getDocxTables(parameters.documentPart());
        Tbl table = tables.get(2);
        setTableData(table, data);
    }
}
