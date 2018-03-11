import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataChart {

	private Robot robot;
	private JFrame frame;
	private ArrayList<String[]> data;
	private final int WIDTH = 1400, HEIGHT = 500;

	public DataChart(Competition competition, String t) {

		frame = new JFrame("Team Number: " + t);
		robot = competition.getBot(t);
		this.data = robot.data;

		initCharts();

		frame.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT, WIDTH, HEIGHT);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}

	public DataChart() {

		frame = new JFrame("Data Chart");

		initCharts();

		frame.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT, WIDTH, HEIGHT);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		new DataChart();
	}

	private void initCharts() {

		ChartPanel chartPanel = new ChartPanel(createChart(0));
		ChartPanel chartPanel2 = new ChartPanel(createChart(1));
		chartPanel.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().setLayout(new BorderLayout());

		frame.add(chartPanel, BorderLayout.LINE_START);
		frame.add(chartPanel2, BorderLayout.LINE_END);

	}

	private XYSeries createDataset(int which) {
		XYSeries series = null;
		System.out.println("SIZE: " + data.size());

		if (which == 1) {

			series = new XYSeries("Auto Scale");

			for (int y = 0; y < data.size(); y++) {
				series.add(y, Integer.parseInt(data.get(y)[which]));
			}

		} else if (which == 2) {

			series = new XYSeries("Auto Switch");
			for (int y = 0; y < data.size(); y++) {
				series.add(y, Integer.parseInt(data.get(y)[which]));
			}

		} else if (which == 5) {
			series = new XYSeries("Tele Scale");
			for (int y = 0; y < data.size(); y++) {
				series.add(y, Integer.parseInt(data.get(y)[which]));
			}

		} else if (which == 6) {
			series = new XYSeries("Tele Switch");

			for (int y = 0; y < data.size(); y++) {
				series.add(y, (Integer.parseInt(data.get(y)[which]) + Integer.parseInt(data.get(y)[which])) / 2);
			}
		} else if (which == 8) {
			series = new XYSeries("Climb");
			for (int y = 0; y < data.size(); y++) {
				series.add(y, Integer.parseInt(data.get(y)[which]));
			}

		}

		return series;

	}

	private JFreeChart createChart(int which) {

		JFreeChart chart = null;
		XYSeriesCollection dataset = new XYSeriesCollection();

		if (which == 0) {

			dataset.addSeries(createDataset(1));
			dataset.addSeries(createDataset(2));

			chart = ChartFactory.createXYLineChart("Auto Stats", "Competition", "Values", dataset,
					PlotOrientation.VERTICAL, true, true, false);
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

		} else if (which == 1) {
			dataset.addSeries(createDataset(5));
			dataset.addSeries(createDataset(6));
			dataset.addSeries(createDataset(8));

			chart = ChartFactory.createXYLineChart("Tele Stats", "Competition", "Values", dataset,
					PlotOrientation.VERTICAL, true, true, false);
			XYPlot plot = chart.getXYPlot();

			XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

			renderer.setSeriesPaint(0, Color.RED);
			renderer.setSeriesStroke(0, new BasicStroke(2.0f));

			renderer.setSeriesPaint(1, Color.BLUE);
			renderer.setSeriesStroke(1, new BasicStroke(2.0f));

			renderer.setSeriesPaint(2, Color.GREEN);
			renderer.setSeriesStroke(1, new BasicStroke(2.0f));

			plot.setRenderer(renderer);
			plot.setBackgroundPaint(Color.white);

			plot.setRangeGridlinesVisible(false);
			plot.setDomainGridlinesVisible(false);

			chart.getLegend().setFrame(BlockBorder.NONE);
		}

		return chart;

	}

	private double getLowestLow(DefaultHighLowDataset dataset) {
		double lowest;
		lowest = dataset.getLowValue(0, 0);
		for (int i = 1; i < dataset.getItemCount(0); i++) {
			if (dataset.getLowValue(0, i) < lowest) {
				lowest = dataset.getLowValue(0, i);
			}
		}

		return lowest;
	}

	private double getHighestHigh(DefaultHighLowDataset dataset) {
		double highest;
		highest = dataset.getHighValue(0, 0);
		for (int i = 1; i < dataset.getItemCount(0); i++) {
			if (dataset.getLowValue(0, i) > highest) {
				highest = dataset.getHighValue(0, i);
			}
		}

		return highest;
	}

}
