package org.deha.be.service.domain.report.command;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.report.CreateLineChartCommandParameters;
import org.deha.be.dto.report.ReplacePlaceholdersCommandParameters;
import org.deha.be.dto.report.SetTableCommandParameters;
import org.deha.be.enums.TurkishCity;
import org.deha.be.enums.TurkishMonth;
import org.deha.be.model.Report;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.ReportRepository;
import org.deha.be.service.domain.report.command.chart.CreateLineChartCommand;
import org.deha.be.service.domain.report.command.chart.CreatePieChartCommand;
import org.deha.be.service.domain.report.command.table.SetMaxMinPredictionTableCommand;
import org.deha.be.service.domain.report.command.table.SetPredictionsTableCommand;
import org.deha.be.service.domain.report.command.table.SetStatisticsTableCommand;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.time.Year;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateMonthlyCityReportCommand {
    private final ReportRepository reportRepository;
    private final ReplacePlaceholdersCommand replacePlaceholdersCommand;
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
        try {
            InputStream templateStream = getClass().getClassLoader().getResourceAsStream(reportTemplatePath);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateStream);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            ReplacePlaceholdersCommandParameters replacePlaceholdersCommandParameters = new ReplacePlaceholdersCommandParameters(
                    wordMLPackage, earthquakes, city, month
            );
            SetTableCommandParameters tableCommandParameters = new SetTableCommandParameters(earthquakes, documentPart);
            replacePlaceholdersCommand.execute(replacePlaceholdersCommandParameters);
            setMaxMinPredictionTableCommand.execute(tableCommandParameters);
            setStatisticsTableCommand.execute(tableCommandParameters);
            setPredictionsTableCommand.execute(tableCommandParameters);
            createLineChartCommand.execute(new CreateLineChartCommandParameters(earthquakes, wordMLPackage));
            createPieChartCommand.execute(wordMLPackage);

            String filePath = reportOutputFolderPath + city + "_" + month + ".docx";
            File reportFile = new File(filePath);
            wordMLPackage.save(reportFile);

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
