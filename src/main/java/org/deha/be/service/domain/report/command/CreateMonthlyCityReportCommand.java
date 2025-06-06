package org.deha.be.service.domain.report.command;

import de.phip1611.Docx4JSRUtil;
import lombok.RequiredArgsConstructor;
import org.deha.be.dto.report.CreateLineChartCommandParameters;
import org.deha.be.dto.report.SetTableCommandParameters;
import org.deha.be.enums.TurkishCity;
import org.deha.be.enums.TurkishMonth;
import org.deha.be.model.Report;
import org.deha.be.model.earthquakes.BaseEarthquake;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.ReportRepository;
import org.deha.be.service.domain.report.command.chart.CreateLineChartCommand;
import org.deha.be.service.domain.report.command.chart.CreatePieChartCommand;
import org.deha.be.service.domain.report.command.table.SetMaxMinPredictionTableCommand;
import org.deha.be.service.domain.report.command.table.SetPredictionsTableCommand;
import org.deha.be.service.domain.report.command.table.SetStatisticsTableCommand;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateMonthlyCityReportCommand {
    private final ReportRepository reportRepository;
    private final SetMaxMinPredictionTableCommand setMaxMinPredictionTableCommand;
    private final CreateLineChartCommand createLineChartCommand;
    private final CreatePieChartCommand createPieChartCommand;
    private final SetStatisticsTableCommand setStatisticsTableCommand;
    private final SetPredictionsTableCommand setPredictionsTableCommand;
    @Value("${report.template_path}")
    private String reportTemplatePath;
    @Value("${report.output_path}")
    private String reportOutputFolderPath;

    public void execute(List<PredictedEarthquake> earthquakes) {
        TurkishCity city = earthquakes.getFirst().getLocation().getCity();
        String month = TurkishMonth.valueOf(earthquakes.getFirst().getOccurrenceDate().atOffset(ZoneOffset.UTC).getMonth().toString()).getName();

        float minMagnitude = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(Float.MAX_VALUE, Math::min);
        float maxMagnitude = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(Float.MIN_VALUE, Math::max);
        float range = maxMagnitude - minMagnitude;

        Map<String, String> replacements = new HashMap<>();
        replacements.put("${REPORT_CITY}", city.getValue());
        replacements.put("${REPORT_MONTH}", month);
        replacements.put("${MG_RANGE}", String.valueOf(range));

        try {
            InputStream templateStream = getClass().getClassLoader().getResourceAsStream(reportTemplatePath);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);
            VariablePrepare.prepare(wordMLPackage);
            Docx4JSRUtil.searchAndReplace(wordMLPackage, replacements);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            SetTableCommandParameters tableCommandParameters = new SetTableCommandParameters(earthquakes, documentPart);
            setMaxMinPredictionTableCommand.execute(tableCommandParameters);
            setStatisticsTableCommand.execute(tableCommandParameters);
            setPredictionsTableCommand.execute(tableCommandParameters);
            createLineChartCommand.execute(new CreateLineChartCommandParameters(earthquakes, wordMLPackage));
            createPieChartCommand.execute(wordMLPackage);

            String filePath = reportOutputFolderPath + city + "_" + month + ".pdf";
            File reportFile = new File(filePath);

            try (OutputStream os = new FileOutputStream(reportFile)) {
                Docx4J.toPDF(wordMLPackage, os);
            }

            Report report = new Report();
            report.setCity(city);
            report.setDate(Year.now() + "-" + month);
            report.setTitle(city.getValue() + " " + month + " Ayı Raporu");
            report.setFilePath(filePath);
            reportRepository.save(report);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
