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
        alertPanel.setVisible(true);
    }
    
    public void initChart(TimeSeries data, TimeSeries sma) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
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
        System.out.println("Renderizando gráfico: " + this.name); ///////////////////////////////////////////////////////////////////////////
        if ((Double) data.getValue(data.getItemCount()-1) > (Double) sma.getValue(sma.getItemCount()-1)) {
            alertPanel.setBuy();
        } else {
            alertPanel.setSell();
        }
        
        alertPanel.setSMA((Double) sma.getValue(sma.getItemCount()-1));
        alertPanel.setValue((Double) data.getValue(data.getItemCount()-1));
        
        this.sma = sma;
        this.data = data;
    }

    
    // Getters and setters
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
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
    public AlertPanel getAlertPanel() {        
        return alertPanel;
    }
    
    // Properties
    private AlertPanel alertPanel;
    private TimeSeries data;
    private TimeSeries sma;
    private JFreeChart chart;
    private String name;
    private String code;
}
