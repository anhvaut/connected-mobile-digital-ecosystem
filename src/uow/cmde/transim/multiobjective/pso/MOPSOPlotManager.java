package uow.cmde.transim.multiobjective.pso;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Guest
 */
public class MOPSOPlotManager {

    private XYSeries xySeriesExternalArchive;
    private XYSeriesCollection xySeriesCollectionExternalArchive;
    private JFreeChart jFreeChartExternalArchive;
    private ChartPanel chartPanelExternalArchive;
    private XYPlot xyPlotExternalArchive;
    private XYLineAndShapeRenderer xyLineAndShapeRendererExternalArchive;
    private NumberAxis numberAxisExternalArchive;

    private XYSeries xySeriesSwarm;
    private XYSeriesCollection xySeriesCollectionSwarm;
    private JFreeChart jFreeChartSwarm;
    private ChartPanel chartPanelSwarm;
    private XYPlot xyPlotSwarm;
    private XYLineAndShapeRenderer xyLineAndShapeRendererSwarm;
    private NumberAxis numberAxisSwarm;
    private boolean debug;
    private double pointsize;



    public MOPSOPlotManager() {
    }

    public void setupPlotExternalArchive() {
        pointsize = 2.0;
        xySeriesExternalArchive = new XYSeries("Solução");
        xySeriesCollectionExternalArchive = new XYSeriesCollection(xySeriesExternalArchive);
        setJFreeChartExternalArchive(ChartFactory.createXYLineChart("no. de chamadas: 0", "F2", "F1", xySeriesCollectionExternalArchive, PlotOrientation.VERTICAL, true, true, false));
        xyPlotExternalArchive = (XYPlot) jFreeChartExternalArchive.getPlot();
        xyPlotExternalArchive.setBackgroundPaint( Color.WHITE );
        xyPlotExternalArchive.setDomainGridlinePaint(Color.GRAY);
        xyPlotExternalArchive.setRangeGridlinePaint(Color.GRAY);
        xyPlotExternalArchive.getRenderer().setSeriesPaint(0, Color.BLACK);
        xyPlotExternalArchive.getRenderer().setSeriesShape(0, new Ellipse2D.Double(pointsize/2.0*(-1),pointsize/2.0*(-1),pointsize,pointsize));
        xyLineAndShapeRendererExternalArchive = (XYLineAndShapeRenderer) xyPlotExternalArchive.getRenderer();
        xyLineAndShapeRendererExternalArchive.setBaseShapesVisible(true);
        xyLineAndShapeRendererExternalArchive.setBaseShapesFilled(true);
//        xyLineAndShapeRendererExternalArchive.
//        xyLineAndShapeRendererExternalArchive.set
        xyLineAndShapeRendererExternalArchive.setBaseLinesVisible(false);
        numberAxisExternalArchive =(NumberAxis) xyPlotExternalArchive.getRangeAxis();
        numberAxisExternalArchive.setLabelAngle(Math.PI/2);
        numberAxisExternalArchive.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chartPanelExternalArchive = new ChartPanel(getJFreeChartExternalArchive());
    }

    public void setupPlotSwarm() {
        xySeriesSwarm = new XYSeries("Particle");
        xySeriesCollectionSwarm = new XYSeriesCollection(xySeriesSwarm);
        jFreeChartSwarm = ChartFactory.createXYLineChart("no. de chamadas: 0", "X", "Y", xySeriesCollectionSwarm, PlotOrientation.VERTICAL, true, true, false);
        xyPlotSwarm = (XYPlot) jFreeChartSwarm.getPlot();
        xyLineAndShapeRendererSwarm = (XYLineAndShapeRenderer) xyPlotSwarm.getRenderer();
        xyLineAndShapeRendererSwarm.setBaseShapesVisible(true);
        xyLineAndShapeRendererSwarm.setBaseShapesFilled(true);
//        xylineandshaperenderer.setBaseShape(new Shape());
        xyLineAndShapeRendererSwarm.setBaseLinesVisible(false);
        numberAxisSwarm = (NumberAxis) xyPlotSwarm.getRangeAxis();
        numberAxisSwarm.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        chartPanelSwarm = new ChartPanel(jFreeChartSwarm);
    }

    public void plotExternalArchive(ArrayList<Position> externalArchive, int currentNCallObjectiveFunction) {
        xySeriesExternalArchive.clear();

       
        try
        {
        	
	        if ((externalArchive.size() != 0) && (externalArchive.get(0).getFitness().length >= 2)) {
	            for (int i = 0; i < externalArchive.size(); i++) {
	                xySeriesExternalArchive.add(externalArchive.get(i).getFitness()[0], externalArchive.get(i).getFitness()[1]);
	               // System.out.println(externalArchive.get(i).getFitness()[0] + "--" + externalArchive.get(i).getFitness()[1]);
	            }
	            getJFreeChartExternalArchive().setTitle("no. de chamadas: " + currentNCallObjectiveFunction);
	        }
	        
        	 
        	 getJFreeChartExternalArchive().setTitle("no. de chamadas: " + currentNCallObjectiveFunction);
        }
        catch(Exception ex)
        {
        	System.out.println("plotExternalArchive:" + ex.getMessage());
        }
        
    }

    public void plotSwarm(Particle[] swarm, int currentNCallObjectiveFunction) {
        xySeriesSwarm.clear();

        try
        {
	        if ((swarm.length != 0) && (swarm[0].getPosition().getFitness().length >= 2)) {
	            for (int i = 0; i < swarm.length; i++) {
	                xySeriesSwarm.add(swarm[i].getPosition().getFitness()[0], swarm[i].getPosition().getFitness()[1]);
	                //System.out.println(swarm[i].getPosition().getFitness()[0] + "--" + swarm[i].getPosition().getFitness()[1]);
	            }
	            jFreeChartSwarm.setTitle("no. de chamadas: " + currentNCallObjectiveFunction);
	        }
        }
        catch(Exception ex)
        {
        	System.out.println("plotSwarm:" + ex.getMessage());
        }
    }




    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * @return the chartPanelExternalArchive
     */
    public ChartPanel getChartPanelExternalArchive() {
        return chartPanelExternalArchive;
    }

    /**
     * @param chartPanelExternalArchive the chartPanelExternalArchive to set
     */
    public void setChartPanelExternalArchive(ChartPanel chartPanelExternalArchive) {
        this.chartPanelExternalArchive = chartPanelExternalArchive;
    }

    /**
     * @return the chartPanelSwarm
     */
    public ChartPanel getChartPanelSwarm() {
        return chartPanelSwarm;
    }

    /**
     * @param chartPanelSwarm the chartPanelSwarm to set
     */
    public void setChartPanelSwarm(ChartPanel chartPanelSwarm) {
        this.chartPanelSwarm = chartPanelSwarm;
    }

  
  
  
    public JFreeChart getJFreeChartExternalArchive() {
        return jFreeChartExternalArchive;
    }

    public void setJFreeChartExternalArchive(JFreeChart jFreeChartExternalArchive) {
        this.jFreeChartExternalArchive = jFreeChartExternalArchive;
    }

    public NumberAxis getNumberAxisExternalArchive() {
        return numberAxisExternalArchive;
    }

    public void setNumberAxisExternalArchive(NumberAxis numberAxisExternalArchive) {
        this.numberAxisExternalArchive = numberAxisExternalArchive;
    }
}
