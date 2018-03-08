import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataChart {

	private Robot robot;
	private JFrame frame;
	private ArrayList<String[]> data;
	private final int WIDTH = 900, HEIGHT = 500;

	public DataChart(Competition competition, String t) {

		frame = new JFrame("Data Chart");
		robot = competition.getBot(t);
		this.data = robot.data;

		initCharts();

		frame.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - WIDTH,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - HEIGHT, WIDTH, HEIGHT);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JFreeChart chart = createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		frame.add(chartPanel);

		frame.pack();

	}

	private XYDataset createDataset(int which) {
		XYSeries series = null;
		System.out.println("SIZE: " + data.size());
		for (int y = 0; y < data.size(); y++) {
			if (which == 1) {

				series = new XYSeries("Auto Switch");

				series.add(y, Integer.parseInt(data.get(y)[which]));

			} else if (which == 2) {

				series = new XYSeries("Auto Scale");

				series.add(y, Integer.parseInt(data.get(y)[which]));


			} else if (which == 4) {
				series = new XYSeries("Switch Red");

				series.add(y, Integer.parseInt(data.get(y)[which]));

			} else if (which == 5) {
				series = new XYSeries("Tele Scale");

				series.add(y, Integer.parseInt(data.get(y)[which]));

			} else if (which == 6) {
				series = new XYSeries("Switch Blue");

				series.add(y, Integer.parseInt(data.get(y)[which]));

			} else if (which == 8) {
				series = new XYSeries("Climb");

				series.add(y, Integer.parseInt(data.get(y)[which]));

			}
		}

		XYSeriesCollection dataset = new XYSeriesCollection();

		dataset.addSeries(series);

		return dataset;
	}

	private JFreeChart createChart() {

		// create subplot 1...
		final XYDataset data1 = createDataset(1);
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		final NumberAxis rangeAxis1 = new NumberAxis("Matches");
		final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
		subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		subplot1.setDataset(1, createDataset(2));
		subplot1.setRenderer(1, new StandardXYItemRenderer());
		// create subplot 2...
		final XYDataset data2 = createDataset(4);
		final XYItemRenderer renderer2 = new StandardXYItemRenderer();
		final NumberAxis rangeAxis2 = new NumberAxis("Matches");
		rangeAxis2.setAutoRangeIncludesZero(false);
		final XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
		subplot2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		subplot2.setDataset(1, createDataset(5));
		subplot2.setDataset(1, createDataset(6));
		subplot2.setDataset(1, createDataset(8));

		final NumberAxis axis2 = new NumberAxis("Values");
		axis2.setAutoRangeIncludesZero(false);
		subplot2.setDomainAxis(1, axis2);
		subplot2.setDomainAxisLocation(1, AxisLocation.TOP_OR_LEFT);
		subplot2.setRenderer(1, new StandardXYItemRenderer());
		subplot2.mapDatasetToRangeAxis(1, 1);

		// parent plot...
		final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Values"));
		plot.setGap(10.0);

		// add the subplots...
		plot.add(subplot1, 1);
		plot.add(subplot2, 1);
		plot.setOrientation(PlotOrientation.HORIZONTAL);

		// return a new chart containing the overlaid plot...
		return new JFreeChart("Robot Data", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

	}

}
