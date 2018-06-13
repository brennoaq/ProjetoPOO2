package projeto_poo2;

import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Equity {
    public Equity(String name, String code) {
        this.name = name;
        this.code = code;
        
        this.alertPanel = new AlertPanel(name);
    }
    
    public void initChart(TimeSeries data, TimeSeries sma) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        this.sma = sma;
        this.data = data;
        dataset.addSeries(sma);
        dataset.addSeries(data);
        this.chart = ChartFactory.createTimeSeriesChart(
                this.getName(),
                "Time",
                "Value",
                dataset,
                true, false, true);
        
        
        XYPlot plot = this.chart.getXYPlot();
        plot.getRangeAxis().setRange(Math.min(sma.getMinY(), data.getMinY()),
                                     Math.max(sma.getMaxY(), data.getMaxY()));
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);

        renderer.setSeriesStroke(0, new BasicStroke(0.5f));
        renderer.setSeriesStroke(1, new BasicStroke(0.5f));
        
        plot.setRenderer(renderer);
    }

    
    // Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public AlertPanel getAlert() {
        return alert;
    }
    
    public void updateState() {

    }
    
    public TimeSeries getData() {
        return data;
    }

    public TimeSeries getSma() {
        return sma;
    }

    public JFreeChart getChart() {
        if (chart != null) 
            return chart;
        else
            throw new NullPointerException("You must initiate the chart");
    }
    public AlertPanel getNewAlertPanel() {
        if (data.getDataItem(0).getValue().doubleValue() > sma.getDataItem(0).getValue().doubleValue()) {
            alertPanel.setSell();
        } else {
            alertPanel.setBuy();
        }
        
        return alertPanel;
    }
    
    
    
    // Properties
    private AlertPanel alertPanel;
    private TimeSeries data;
    private TimeSeries sma;
    private JFreeChart chart;
    private String name;
    private String code;
    private AlertPanel alert;
}
