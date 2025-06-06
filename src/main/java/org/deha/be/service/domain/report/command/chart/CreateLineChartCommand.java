package org.deha.be.service.domain.report.command.chart;

import org.deha.be.dto.report.CreateLineChartCommandParameters;
import org.deha.be.generic.command.EffectCommand;
import org.deha.be.model.earthquakes.PredictedEarthquake;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateLineChartCommand extends BaseChartCommand implements EffectCommand<CreateLineChartCommandParameters> {
    @Override
    public void execute(CreateLineChartCommandParameters createLineChartCommandParameters) {
        try {
            JFreeChart chart = createLineChart(createLineChartCommandParameters.earthquakes());
            byte[] chartImageBytes = chartToByteArray(chart, 500, 400);
            addImageToDocument(createLineChartCommandParameters.wordMLPackage(), chartImageBytes, "Büyüklük Grafiği");
        } catch (Exception e) {
            throw new RuntimeException("Error creating line chart: " + e.getMessage(), e);
        }
    }

    public JFreeChart createLineChart(List<PredictedEarthquake> earthquakes) {
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
        XYSeries series = new XYSeries("Büyüklük");

        // Add data points matching your chart
        for (int i = 0; i < xAxis.length; i++) {
            series.add(xAxis[i], yAxis[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Büyüklük Grafiği",                           // No main title
                null,                           // No X-axis label
                null,                           // No Y-axis label
                dataset,                        // Dataset
                PlotOrientation.VERTICAL,       // Orientation
                true,                          // Include legend
                false,                         // No tooltips
                false                          // No URLs
        );

        chart.setBackgroundPaint(new Color(255, 255, 255)); // White
        chart.setBorderVisible(false);

        // Get the plot for detailed customization
        XYPlot plot = chart.getXYPlot();

        // Set background and grid
        plot.setBackgroundPaint(new Color(255, 255, 255)); // White
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinePaint(new Color(211, 211, 211)); // Light gray
        plot.setRangeGridlinePaint(new Color(211, 211, 211)); // Light gray
        plot.setOutlineVisible(true);
        plot.setOutlinePaint(new Color(0, 0, 0)); // Black

        // Customize the renderer for blue color and diamond markers
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, new Color(0, 123, 255)); // Blue color
        renderer.setSeriesStroke(0, new BasicStroke(2.0f)); // Line thickness

        // Set diamond-shaped markers
        renderer.setSeriesShape(0, new Polygon(
                new int[]{0, 4, 0, -4}, new int[]{-4, 0, 4, 0}, 4));
        renderer.setSeriesShapesFilled(0, true);

        plot.setRenderer(renderer);

        // Customize axes
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // Set X-axis range and ticks
        domainAxis.setRange(0, 30);
        domainAxis.setTickUnit(new NumberTickUnit(3));
        domainAxis.setAxisLineVisible(true);
        domainAxis.setTickMarksVisible(true);
        domainAxis.setTickLabelsVisible(true);

        // Set Y-axis range and ticks
        rangeAxis.setRange(0, 5);
        rangeAxis.setTickUnit(new NumberTickUnit(1.25));
        rangeAxis.setAxisLineVisible(true);
        rangeAxis.setTickMarksVisible(true);
        rangeAxis.setTickLabelsVisible(true);

        // Position legend at top
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.TOP);
        legend.setFrame(BlockBorder.NONE);

        return chart;
    }
}
