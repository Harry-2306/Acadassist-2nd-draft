package edu.univ.erp.ui;


import edu.univ.erp.domain.Gradestats;
import edu.univ.erp.domain.StudentGrade;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class ChartWindow extends JFrame {

    public ChartWindow(Gradestats stats, String courseId) {
        setTitle("Course Charts - " + courseId);
        setSize(800, 1200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 0, 20));

        mainPanel.add(createHistogram(stats));
        mainPanel.add(createBarChart(stats));
        mainPanel.add(createLineChart(stats));
        mainPanel.add(createPieChart(stats));

        JScrollPane scrollPane = new JScrollPane(
                mainPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane);
        setVisible(true);
    }

    private ChartPanel createHistogram(Gradestats stats) {
        HistogramDataset dataset = new HistogramDataset();
        double[] values = stats.finalMarks.stream().mapToDouble(Double::doubleValue).toArray();
        dataset.addSeries("Marks", values, 10);

        JFreeChart chart = ChartFactory.createHistogram(
                "Marks Histogram",
                "Marks",
                "Frequency",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        return new ChartPanel(chart);
    }

    private ChartPanel createBarChart(Gradestats stats) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(stats.gradeACount, "Grades", "A");
        dataset.addValue(stats.gradeBCount, "Grades", "B");
        dataset.addValue(stats.gradeCCount, "Grades", "C");
        dataset.addValue(stats.gradeDCount, "Grades", "D");
        dataset.addValue(stats.gradeFCount, "Grades", "F");

        JFreeChart chart = ChartFactory.createBarChart(
                "Grade Distribution",
                "Grade",
                "Number of Students",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        return new ChartPanel(chart);
    }

    private ChartPanel createLineChart(Gradestats stats) {
        XYSeries series = new XYSeries("Final Marks");
        int studentNum = 1;
        for (double mark : stats.finalMarks) {
            series.add(studentNum++, mark);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Marks Trend",
                "Student Index",
                "Marks",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        return new ChartPanel(chart);
    }

    private ChartPanel createPieChart(Gradestats stats) {
        DefaultPieDataset dataset = new DefaultPieDataset<>();
        dataset.setValue("A", stats.gradeACount);
        dataset.setValue("B", stats.gradeBCount);
        dataset.setValue("C", stats.gradeCCount);
        dataset.setValue("D", stats.gradeDCount);
        dataset.setValue("F", stats.gradeFCount);

        JFreeChart chart = ChartFactory.createPieChart(
                "Grade Percentages",
                dataset,
                false,
                false,
                false
        );

        return new ChartPanel(chart);
    }
}
