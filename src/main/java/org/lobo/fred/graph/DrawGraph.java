package org.lobo.fred.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class DrawGraph {
    protected static void draw(List<Map<String, Map<String, Double>>> dataMapsList) {
//        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
//
//        // Iterate over all series, returning a TimeSeries of data
//        for (Map.Entry<String, Map<String, Double>> entry :
//                dataMaps.entrySet()) {
//            timeSeriesCollection.addSeries(getTimeSeries(entry.getKey(), entry.getValue()));
//        }
//
//        JFreeChart objChart = ChartFactory.createTimeSeriesChart("Chart",
//                "Year",
//                "Data",
//                timeSeriesCollection);
//
//        ChartFrame frame = new ChartFrame("via FRED", objChart);
//        frame.setPreferredSize(new Dimension(1550, 800));
//        frame.pack();
//        frame.setVisible(true);

        if (dataMapsList.isEmpty())
            return;

        int yAxisIndex = 0;
        JFreeChart chart = null;
        XYPlot plot = null;
        for (Map<String, Map<String, Double>> dataMaps : dataMapsList) {
            TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

            // Iterate over all series, returning a TimeSeries of data
            for (Map.Entry<String, Map<String, Double>> entry : dataMaps.entrySet()) {
                timeSeriesCollection.addSeries(getTimeSeries(entry.getKey(), entry.getValue()));
            }

            // First time series
            if (yAxisIndex == 0) {
                chart = ChartFactory.createTimeSeriesChart(null, null, null, timeSeriesCollection);
                plot = (XYPlot) chart.getPlot();
            }

            // Subsequent time series
            else {
                NumberAxis axisY = new NumberAxis();
                plot.setRangeAxis(yAxisIndex, axisY);
                plot.setDataset(yAxisIndex, timeSeriesCollection);
                plot.mapDatasetToRangeAxis(yAxisIndex, yAxisIndex);

                // Add a visible dot to every plot point
                StandardXYItemRenderer renderer = new StandardXYItemRenderer();
                //renderer.setSeriesShape(0, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
                //renderer.setBaseShapesVisible(true);
                plot.setRenderer(yAxisIndex, renderer);
            }

            yAxisIndex++;
        }

        ChartFrame frame = new ChartFrame("via FRED", chart);
        frame.setPreferredSize(new Dimension(1550, 800));
        frame.pack();
        frame.setVisible(true);
    }

    private static TimeSeries getTimeSeries(String name, Map<String, Double> dataMap) {
        TimeSeries series = new TimeSeries(name);

        // Iterate over the data
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, Double> entry : dataMap.entrySet()) {
            try {
                series.add(new Day(simpleDateFormat.parse(entry.getKey())), entry.getValue());
            }
            catch (ParseException e) {
                System.out.println("Bad date format: " + entry.getKey());
            }
        }
        return series;
    }
}
