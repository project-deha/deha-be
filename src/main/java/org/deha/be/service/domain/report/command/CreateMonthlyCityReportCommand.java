package org.deha.be.service.domain.report.command;

import de.phip1611.Docx4JSRUtil;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.deha.be.enums.TurkishCity;
import org.deha.be.enums.TurkishMonth;
import org.deha.be.model.Report;
import org.deha.be.model.earthquakes.BaseEarthquake;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.deha.be.repository.ReportRepository;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.EmbeddedPackagePart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateMonthlyCityReportCommand {
    private final ReportRepository reportRepository;
    @Value("${report.template_path}")
    private String reportTemplatePath;
    @Value("${report.output_path}")
    private String reportOutputFolderPath;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
            List<Tbl> tables = getDocxTables(documentPart);
            setMaxMinPredictedEarthquakeTable(earthquakes, tables);
            setStatisticsTable(earthquakes, tables);
            setPredictionsTable(earthquakes, tables);
            setLineGraph(earthquakes, wordMLPackage);

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

    private void setMaxMinPredictedEarthquakeTable(List<PredictedEarthquake> earthquakes, List<Tbl> tables) {
        PredictedEarthquake biggestMagnitudeEarthquake = earthquakes.stream()
                .reduce((first, second) -> first.getMagnitude() > second.getMagnitude() ? first : second)
                .orElse(null);

        PredictedEarthquake lowestMagnitudeEarthquake = earthquakes.stream()
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

        Tbl maxTable = tables.get(0);
        Tbl minTable = tables.get(1);

        setTableData(maxTable, maxData);
        setTableData(minTable, minData);
    }

    private void setStatisticsTable(List<PredictedEarthquake> earthquakes, List<Tbl> tables) {
        float sumMagnitude = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .reduce(0F, Float::sum);

        float avgMagnitude = sumMagnitude / earthquakes.size();

        float variance = earthquakes.stream()
                .map(BaseEarthquake::getMagnitude)
                .map(magnitude -> (magnitude - avgMagnitude) * (magnitude - avgMagnitude))
                .reduce(0F, Float::sum) / (earthquakes.size() - 1);

        float standardDeviation = (float) Math.sqrt(variance);

        String[][] data = {{
                String.valueOf(sumMagnitude),
                String.valueOf(earthquakes.size()),
                String.valueOf(avgMagnitude),
                String.valueOf(standardDeviation),
                String.valueOf(variance),
        }};

        Tbl table = tables.get(2);
        setTableData(table, data);
    }

    private void setLineGraph(List<PredictedEarthquake> earthquakes, WordprocessingMLPackage wordMLPackage) throws IOException {
        List<Integer> xAxisList = new ArrayList<>();
        List<Float> yAxisList = new ArrayList<>();

        for (int i = 0; i < earthquakes.size(); i++) {
            if (i % 3 == 0) {
                yAxisList.add(earthquakes.get(i).getMagnitude());
                xAxisList.add(i);
            }
        }

        int[] xAxis = xAxisList.stream().mapToInt(Integer::intValue).toArray();

        float[] yAxis = new float[yAxisList.size()];
        for (int i = 0; i < yAxisList.size(); i++) {
            yAxis[i] = yAxisList.get(i);
        }

        for (Part part : wordMLPackage.getParts().getParts().values()) {
            if (part instanceof EmbeddedPackagePart) {
                EmbeddedPackagePart epp = (EmbeddedPackagePart) part;

                if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(epp.getContentType())) {
                    byte[] excelBytes = epp.getBytes();
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(excelBytes);
                    Workbook workbook = new XSSFWorkbook(inputStream);
                    Sheet sheet = workbook.getSheetAt(0);

                    // Clear existing rows
                    for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row != null) sheet.removeRow(row);
                    }

                    Row headerRow = sheet.createRow(0);
                    Row dataRow = sheet.createRow(1);
                    headerRow.createCell(0).setCellValue("");
                    dataRow.createCell(0).setCellValue("Büyüklük");

                    for (int i = 0; i < xAxis.length; i++) {
                        headerRow.createCell(i + 1).setCellValue(xAxis[i]);
                        dataRow.createCell(i + 1).setCellValue(yAxis[i]);
                    }

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    workbook.write(bos);
                    workbook.close();

                    epp.setBinaryData(bos.toByteArray());
                }
            }
        }
    }

    private void setPredictionsTable(List<PredictedEarthquake> earthquakes, List<Tbl> tables) {
        String[][] data = new String[earthquakes.size()][4];

        for (int i = 0; i < earthquakes.size(); i++) {
            PredictedEarthquake earthquake = earthquakes.get(i);
            data[i][0] = formatter.format(earthquake.getOccurrenceDate().atZone(ZoneOffset.UTC));
            data[i][1] = String.valueOf(earthquake.getMagnitude());
            data[i][2] = String.valueOf(earthquake.getDepth());
            data[i][3] = String.valueOf(earthquake.getLocation().getCity());
        }

        Tbl table = tables.get(3);
        setTableData(table, data);
    }

    private List<Tbl> getDocxTables(MainDocumentPart documentPart) {
        List<Tbl> tableList = new ArrayList<>();

        // Get all body elements
        List<Object> bodyElements = documentPart.getContent();

        for (Object obj : bodyElements) {
            if (obj instanceof Tbl) {
                tableList.add((Tbl) obj);
            } else if (obj instanceof JAXBElement &&
                    ((JAXBElement<?>) obj).getValue() instanceof Tbl) {
                tableList.add((Tbl) ((JAXBElement<?>) obj).getValue());
            }
        }

        return tableList;
    }

    private void setTableData(Tbl table, String[][] data) {
        List<Object> rows = table.getContent();
        int rowIndex = 1; // Start from the first row after header
        int dataRowIndex = 0;

        while (rowIndex < rows.size() && dataRowIndex < data.length) {
            Tr row = (Tr) rows.get(rowIndex);
            fillRow(row, data[dataRowIndex]);
            rowIndex++;
            dataRowIndex++;
        }
    }

    private void setCellText(Tc cell, String text) {
        // Check if the cell has any paragraphs
        if (cell.getContent().isEmpty()) {
            // Create a new paragraph if none exists
            P p = new P();
            cell.getContent().add(p);
        }

        // Get the first paragraph
        Object firstParaObj = cell.getContent().getFirst();
        P firstPara;

        if (firstParaObj instanceof JAXBElement) {
            firstPara = (P) ((JAXBElement<?>) firstParaObj).getValue();
        } else {
            firstPara = (P) firstParaObj;
        }

        // Clear existing content in paragraph
        firstPara.getContent().clear();

        // Add new run with text
        R run = new R();
        Text textElement = new Text();
        textElement.setValue(text);
        run.getContent().add(textElement);
        firstPara.getContent().add(run);
    }

    private void fillRow(Tr row, String[] cellValues) {
        List<Object> cells = row.getContent();

        for (int i = 0; i < Math.min(cells.size(), cellValues.length); i++) {
            Object cellObj = cells.get(i);
            Tc cell;

            if (cellObj instanceof JAXBElement) {
                cell = (Tc) ((JAXBElement<?>) cellObj).getValue();
            } else {
                cell = (Tc) cellObj;
            }

            // Set the text in the cell
            setCellText(cell, cellValues[i]);
        }
    }
}
