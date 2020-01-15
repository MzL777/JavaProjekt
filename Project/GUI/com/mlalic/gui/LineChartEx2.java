package com.mlalic.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.mlalic.bl.ReportHandler;

import javafx.util.Pair;

public class LineChartEx2 extends JPanel {
	private static final long serialVersionUID = -4166655182939047253L;
	
	private static final ReportHandler REPORT_HANDLER = new ReportHandler();

	public LineChartEx2() throws Exception {
        initUI();
    }

    private void initUI() throws Exception {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        /*pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
    }

    private XYDataset createDataset() throws Exception {

        XYSeries series1 = new XYSeries("Follow-up patients");
        XYSeries series2 = new XYSeries("New patients");

        List<Pair<Integer, Integer>> newPatients = REPORT_HANDLER.getNewPatients();
        List<Pair<Integer, Integer>> followUpPatients = REPORT_HANDLER.getFollowUpPatients();

        followUpPatients.forEach(x -> series1.add(x.getKey(), x.getValue()));
        newPatients.forEach(x -> series2.add(x.getKey(), x.getValue()));
           
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "New and follow-up patients handled", 
                "Today - [Day]", 
                "Count", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, 
                true, 
                false
        );

        XYPlot plot = chart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));        

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("New and follow-up patients handled",
                        new Font("Serif", Font.BOLD, 18)));

        return chart;
    }
}