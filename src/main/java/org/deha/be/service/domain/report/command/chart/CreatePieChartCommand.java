package org.deha.be.service.domain.report.command.chart;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.predictedearthquake.PredictionWeightByMagnitude;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.repository.PredictedEarthquakeRepository;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePieChartCommand extends BaseChartCommand implements EffectCommand<WordprocessingMLPackage> {
    private final PredictedEarthquakeRepository predictedEarthquakeRepository;

    @Override
    public void execute(WordprocessingMLPackage wordprocessingMLPackage) {
        List<PredictionWeightByMagnitude> chartData = predictedEarthquakeRepository.findEarthquakeCountByMagnitudeRanges();

        try {
            addTextParagraph(wordprocessingMLPackage, "");
            JFreeChart chart = createPieChart(chartData);

            // Convert chart to image bytes
            byte[] chartImageBytes = chartToByteArray(chart, 500, 400);

            // Add image to document
            addImageToDocument(wordprocessingMLPackage, chartImageBytes, "Tahmin Büyüklük Dağılımı");
        } catch (Exception e) {
            throw new RuntimeException("Error while creating pie chart: " + e.getMessage(), e);
        }
    }

    public JFreeChart createPieChart(List<PredictionWeightByMagnitude> chartData) {
        // Create dataset for pie chart
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();


        // Populate dataset with data from chartData
        for (PredictionWeightByMagnitude data : chartData) {
            dataset.setValue(data.getMagnitudeRange(), data.getEarthquakeCount());
        }

        // Create the pie chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Tahmin Büyüklük Dağılımı",     // Chart title
                dataset,                    // Dataset
                true,                      // Include legend
                true,                      // Include tooltips
                false                      // No URLs
        );

        // Customize chart appearance
        chart.setBackgroundPaint(new Color(255, 255, 255)); // White background
        chart.getTitle().setPaint(new Color(0, 0, 0)); // Black title

        // Get the pie plot for customization
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255)); // White background
        plot.setOutlineVisible(false);

        // Customize colors for each section
        plot.setSectionPaint("3.0-4.0", new Color(54, 162, 235));   // Blue
        plot.setSectionPaint("4.0-5.5", new Color(255, 99, 132));   // Red
        plot.setSectionPaint("5.5-7.0", new Color(255, 205, 86));   // Yellow
        plot.setSectionPaint("7.0-8.0", new Color(75, 192, 192));   // Teal
        plot.setSectionPaint("Others", new Color(153, 102, 255));     // Purple

        // Show percentages on the pie slices
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator(
                "{0}: {2}", // Format: Name: Percentage
                java.text.NumberFormat.getNumberInstance(),
                java.text.NumberFormat.getPercentInstance()
        ));

        // Customize label appearance
        plot.setLabelFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
        plot.setLabelPaint(new Color(0, 0, 0)); // Black labels

        return chart;
    }
}
