package com.gamedev;

import org.jfree.chart.ChartFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


public class DiagramClass{
    public static ByteArrayOutputStream CreateDiagram(Map<String, String> map) throws IOException{
        int width = 800;
        int height = 600;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsJPEG(out, createChart(createDataset(map)), width, height);
        return out;
    }

    private static PieDataset createDataset(Map<String, String> data){
        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<String, Double> calculatedData = GetPortfolioClass.calcPortfolio(data);
        for (String key: calculatedData.keySet()){
            dataset.setValue(key, calculatedData.get(key));
        }
        return dataset;
    }

    private static JFreeChart createChart( PieDataset dataset ) {
        return ChartFactory.createPieChart(
                "Diagram",   // chart title
                dataset,          // data
                true,             // include legend
                false,
                false);
    }
}
