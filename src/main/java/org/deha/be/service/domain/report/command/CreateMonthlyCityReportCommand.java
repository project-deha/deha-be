package org.deha.be.service.domain.report.command;

import de.phip1611.Docx4JSRUtil;
import lombok.RequiredArgsConstructor;
import org.deha.be.model.earthquakes.BaseEarthquake;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateMonthlyCityReportCommand {
    @Value("${report.template_path}")
    private String reportTemplatePath;
    @Value("${report.output_path}")
    private String reportOutputFolderPath;

    public void execute(List<PredictedEarthquake> earthquakes) {
        String city = earthquakes.getFirst().getLocation().getCity().getValue();
        String month = earthquakes.getFirst().getOccurrenceDate().atOffset(ZoneOffset.UTC).getMonth().toString();
        float avgMagnitude = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(0F, (acc, magnitude) -> acc + magnitude / earthquakes.size());

        float variance = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .map(magnitude -> (magnitude - avgMagnitude) * (magnitude - avgMagnitude))
                .reduce(0F, Float::sum) / (earthquakes.size() - 1);

        float standardDeviation = (float) Math.sqrt(variance);

        float minMagnitude = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(Float.MAX_VALUE, Math::min);
        float maxMagnitude = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(Float.MIN_VALUE, Math::max);
        float range = maxMagnitude - minMagnitude;

        Map<String, String> replacements = new HashMap<>();
        replacements.put("${REPORT_CITY}", city);
        replacements.put("${REPORT_MONTH}", month);
        replacements.put("${MG_RANGE}", String.valueOf(range));

        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(reportTemplatePath));
            VariablePrepare.prepare(wordMLPackage);

            Docx4JSRUtil.searchAndReplace(wordMLPackage, replacements);

            wordMLPackage.save(new File(reportOutputFolderPath + city + "_" + month + ".docx"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
