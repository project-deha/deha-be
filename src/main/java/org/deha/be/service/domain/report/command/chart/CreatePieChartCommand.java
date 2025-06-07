package org.deha.be.service.domain.report.command.chart;

import lombok.RequiredArgsConstructor;
import org.deha.be.dto.predictedearthquake.PredictionWeightByMagnitudeDto;
import org.deha.be.dto.report.CreateChartCommandParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePieChartCommand extends BaseChartCommand implements EffectCommand<CreateChartCommandParameters> {

    @Override
    public void execute(CreateChartCommandParameters parameters) {
        List<PredictionWeightByMagnitudeDto> chartData = prepareChartData(parameters.earthquakes());
        try {
            addTextParagraph(parameters.wordMLPackage(), "");
            JFreeChart chart = createPieChart(chartData);
            byte[] chartImageBytes = chartToByteArray(chart, 500, 400);
            addImageToDocument(parameters.wordMLPackage(), chartImageBytes, "Tahmin Büyüklük Dağılımı");
        } catch (Exception e) {
            throw new RuntimeException("Error while creating pie chart: " + e.getMessage(), e);
        }
    }

    public JFreeChart createPieChart(List<PredictionWeightByMagnitudeDto> chartData) {
        // Create dataset for pie chart
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();


        // Populate dataset with data from chartData
        for (PredictionWeightByMagnitudeDto data : chartData) {
            dataset.setValue(data.magnitudeRange(), data.earthquakeCount());
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
        plot.setSectionPaint("3.0-4.0", new Color(75, 192, 192));
        plot.setSectionPaint("4.0-5.5", new Color(54, 162, 235));
        plot.setSectionPaint("5.5-7.0", new Color(255, 205, 86));
        plot.setSectionPaint("7.0+", new Color(255, 99, 132));

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

    private List<PredictionWeightByMagnitudeDto> prepareChartData(List<PredictedEarthquake> earthquakes) {
        return List.of(
                new PredictionWeightByMagnitudeDto("3.0-4.0", groupByMagnitude(earthquakes, 3.0, 4.0)),
                new PredictionWeightByMagnitudeDto("4.0-5.5", groupByMagnitude(earthquakes, 4.0, 5.5)),
                new PredictionWeightByMagnitudeDto("5.5-7.0", groupByMagnitude(earthquakes, 5.5, 7.0)),
                new PredictionWeightByMagnitudeDto("7.0+", groupByMagnitude(earthquakes, 7.0, 10.0))
        );
    }

    private long groupByMagnitude(List<PredictedEarthquake> earthquakes, double min, double max) {
        return earthquakes.stream()
                .filter(earthquake -> earthquake.getMagnitude() > min && earthquake.getMagnitude() <= max)
                .count();
    }
}
