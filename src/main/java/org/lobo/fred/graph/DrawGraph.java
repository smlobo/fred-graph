package org.lobo.fred.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DrawGraph {
    protected static void draw(Map<String, Map<String, Double>> dataMaps) {
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

        // Iterate over all series, returning a TimeSeries of data
        for (Map.Entry<String, Map<String, Double>> entry :
                dataMaps.entrySet()) {
            timeSeriesCollection.addSeries(getTimeSeries(entry.getKey(), entry.getValue()));
        }

        JFreeChart objChart = ChartFactory.createTimeSeriesChart("Chart",
                "Year",
                "Data",
                timeSeriesCollection);

        ChartFrame frame = new ChartFrame("via FRED", objChart);
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
