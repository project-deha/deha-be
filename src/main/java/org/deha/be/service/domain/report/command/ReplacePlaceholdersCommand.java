package org.deha.be.service.domain.report.command;

import de.phip1611.Docx4JSRUtil;
import org.deha.be.dto.report.ReplacePlaceholdersCommandParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.earthquakes.BaseEarthquake;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReplacePlaceholdersCommand implements EffectCommand<ReplacePlaceholdersCommandParameters> {
    @Override
    public void execute(ReplacePlaceholdersCommandParameters parameters) {
        float minMagnitude = parameters.earthquakes().stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(Float.MAX_VALUE, Math::min);
        float maxMagnitude = parameters.earthquakes().stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(Float.MIN_VALUE, Math::max);
        float range = maxMagnitude - minMagnitude;

        Map<String, String> replacements = new HashMap<>();
        replacements.put("${REPORT_CITY}", parameters.city().getValue());
        replacements.put("${REPORT_MONTH}", parameters.month());
        replacements.put("${MG_RANGE}", String.valueOf(range));

        try {
            VariablePrepare.prepare(parameters.wordMLPackage());
            Docx4JSRUtil.searchAndReplace(parameters.wordMLPackage(), replacements);
        } catch (Exception e) {
            throw new RuntimeException("Error while replacing placeholders in the report template: " + e.getMessage(), e);
        }
    }
}
