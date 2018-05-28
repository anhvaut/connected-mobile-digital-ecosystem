package uow.cmde.transim.transit.outputanalysis;

import java.io.File;
import java.util.*;

import com.csvreader.CsvReader;
import uow.cmde.transim.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection; 

import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.chart.ChartLauncher;

import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Stroke;

public class MOOutputAnalyser {

	  public static String OUTPUT_PATH;
	  public static String OUTPUT_FILE;
	  //private static String inputFile = OUTPUT_PATH + "/" + OUTPUT_FILE;
	  
	  public static class Fitness
	  {
			 public double objective1; 
			 public double objective2; 
			 public double objective3; 
	  }
		
	  public static class Convergence
	  {
			 public double min; 
			 public double max; 
			 public double avg; 
	  }
	
	  public static void readSolution() throws Exception
	  {
		 
		   // System.out.println(inputFile);
		    CsvReader csvReader= new CsvReader( OUTPUT_PATH + "/" + OUTPUT_FILE);
			csvReader.readHeaders();
			
			int i = 0;
			while (csvReader.readRecord())
			{
				String stFirstPopulation="", stLastPopulation ="", stArchive ="";
				
				String stGeneration = csvReader.get("generation");
				
				while(!stGeneration.equals("A"))
				{
					if(stGeneration.equals("1"))
					{
						stFirstPopulation = csvReader.get("population");
					}
					
					if(stGeneration.equals("L"))
					{
						stLastPopulation = csvReader.get("population");
					}
					
					csvReader.readRecord();
					stGeneration = csvReader.get("generation");
				}
				
				 stArchive = csvReader.get("population");
				
				 ArrayList<Fitness> firtPopulationFitness = getFitness(stFirstPopulation);
				 ArrayList<Fitness> lastPopulationFitness = getFitness(stLastPopulation);
				 ArrayList<Fitness> archiveFitness = getFitness(stArchive);
				
				 drawChart("F3F1_" + i, 0,"Action Impact", "Passenger Wait Time", firtPopulationFitness, lastPopulationFitness, archiveFitness );
				 drawChart("F2F1_" + i, 0, "Passenger Comfort", "Passenger Wait Time" ,firtPopulationFitness, lastPopulationFitness, archiveFitness );
				 
				 draw3DPlot("3D" + i,firtPopulationFitness, lastPopulationFitness, archiveFitness);
				 
				 i++;
				 /*
				 for(Fitness f: firtPopulationFitness)
				 {
					 System.out.println(f.objective1 + "  " + f.objective2 + " " + f.objective3);
				 }*/
				
			}
			
			csvReader.close();
	  }
	  
	  public static void readSolution1() throws Exception
	  { 
		  
		    CsvReader csvReader= new CsvReader(OUTPUT_PATH + "/" + OUTPUT_FILE);
			csvReader.readHeaders();
			
			
			int i = 0;
			
			while (csvReader.readRecord())
			{
				
				ArrayList<Convergence> convergence1 = new ArrayList<Convergence>();
				ArrayList<Convergence> convergence2 = new ArrayList<Convergence>();
				ArrayList<Convergence> convergence3 = new ArrayList<Convergence>();
				
				
				
				String stGeneration = csvReader.get("generation");
				while(!stGeneration.equals("A"))
				{
				
					String population = csvReader.get("population");
					ArrayList<Fitness> fitness = getFitness(population);
					
					
					double min1 = fitness.get(0).objective1, min2 = fitness.get(0).objective2, min3 = fitness.get(0).objective3;
				
					for(Fitness f:fitness)
					{
						if(min1>f.objective1) min1= f.objective1;
						if(min2>f.objective2) min2= f.objective2;
						if(min3>f.objective3) min3= f.objective3;
						
					}
					Convergence c1 = new Convergence();
					c1.min = min1;
					
					Convergence c2 = new Convergence();
					c2.min = min2;
					
					Convergence c3 = new Convergence();
					c3.min = min3;
					
					convergence1.add(c1);
					convergence2.add(c2);
					convergence3.add(c3);
					
					csvReader.readRecord();
					stGeneration = csvReader.get("generation");
				}

				drawInterationGraph("Passenger Wait Time","interaction1" + i,convergence1);
				drawInterationGraph("Passenger Comfort","interaction2" + i,convergence2);
				drawInterationGraph("Action Impact","interaction3" + i,convergence3);
				 
				i++;
				
				
			}
			
			csvReader.close();
	  }
	  
	  public static ArrayList<Fitness> getFitness(String st)
	  {
		  StringTokenizer tokenzikerSt = new  StringTokenizer(st,",");
		  
		  ArrayList<Fitness> myFitness = new ArrayList<Fitness>();
		  
		  while(tokenzikerSt.hasMoreTokens()){
				
				String value = tokenzikerSt.nextToken();
				
				StringTokenizer stFitness = new  StringTokenizer(value,";");
				         
				Fitness f=new Fitness();
				f.objective1 = Double.parseDouble(stFitness.nextToken());
				f.objective2 = Double.parseDouble(stFitness.nextToken());
				f.objective3 = Double.parseDouble(stFitness.nextToken());
			
				
				myFitness.add(f);
		  }
		  
		  return myFitness;
		  
	  }
	 
	  
	  public static void drawChart(String fileName, int option, String xTitle, String yTitle, ArrayList<Fitness> firtPopulationFitness,ArrayList<Fitness> lastPopulationFitness,ArrayList<Fitness> archiveFitness  ) throws Exception
	  {
		  XYSeriesCollection dataset = new XYSeriesCollection();
		  
		  
		  XYSeries series1 = new XYSeries("generation");
		  
		  for(Fitness f:firtPopulationFitness)
		  {
			  //series1.add(f.objective3, f.objective1);
			  if(option ==0)
			  {
				  series1.add(f.objective3, f.objective1);
			  }
			  else if(option ==1)
			  {
				  series1.add(f.objective2, f.objective1);
			  }
		  }
		  dataset.addSeries(series1);
		  
		  
		  
		  XYSeries series2 = new XYSeries("Last");
		  for(Fitness f:lastPopulationFitness)
		  {
			  if(option ==0)
			  {
				  series2.add(f.objective3, f.objective1);
			  }
			  else if(option ==1)
			  {
				  series2.add(f.objective2, f.objective1);
			  }
		  }
		  dataset.addSeries(series2);
		  

		  XYSeries series3 = new XYSeries("Archive");
		  for(Fitness f:archiveFitness)
		  {
			  if(option ==0)
			  {
				  series3.add(f.objective3, f.objective1);
			  }
			  else if(option ==1)
			  {
				  series3.add(f.objective2, f.objective1);
			  }
		  }
		  dataset.addSeries(series3);
		  
		  JFreeChart chart = ChartFactory.createScatterPlot(
			  "", // title
			  xTitle, yTitle, // axis labels
			  dataset, // dataset
			  PlotOrientation.VERTICAL,
			  true, // legend? yes
			  true, // tooltips? yes
			  false // URLs? no
		  ); 
		  
		  //CategoryPlot plot = (CategoryPlot) chart.getPlot();
		   //plot.setBackgroundPaint(java.awt.Color.WHITE);
		  
		  ChartUtilities.saveChartAsJPEG(new File( OUTPUT_PATH + "/output/multi-objective/" + fileName +".jpg"), chart, 600, 400);
	  }
	  
	  public static void drawInterationGraph(String title, String fileName, ArrayList<Convergence> convergence) throws Exception
	  {
		  
		  JFreeChart chart =null;
		  DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
		  
		  int interaction = 1;
		  for(Convergence c:convergence)
		  {
			  if(interaction == 10) break;
			  categoryDataset.setValue(c.min,"value",interaction +"");
			  interaction++;
			  
		  }
		  
		  chart = ChartFactory.createLineChart
	                (title, // Title
	                 "Interaction",              // X-Axis label
	                 "Value",// Y-Axis label
	                 categoryDataset,         // Dataset
	                 PlotOrientation.VERTICAL,
	                 true,                     // Show legend
	                 true,
	                 false
	                );
	   
			 
			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			plot.setBackgroundPaint(java.awt.Color.WHITE);
			
			
			Stroke[] seriesStrokeArray = new Stroke[3];
			seriesStrokeArray[0] = new BasicStroke(2.0f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND,
			1.0f, new float[] { 10.0f, 6.0f }, 0.0f);
			
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			renderer.setShapesVisible(true);
			renderer.setDrawOutlines(true);
			renderer.setUseFillPaint(true);
				
			renderer.setSeriesStroke(0, seriesStrokeArray[0]);
			
			ChartUtilities.saveChartAsJPEG(new File( OUTPUT_PATH + "/output/multi-objective/" + fileName +".jpg"), chart, 600, 400);
			 
	  }
	  public static void draw3DPlot( String fileName, ArrayList<Fitness> firtPopulationFitness,ArrayList<Fitness> lastPopulationFitness,ArrayList<Fitness> archiveFitness ) throws Exception
	  {
		    int size = firtPopulationFitness.size() + lastPopulationFitness.size() + archiveFitness.size();
		    int i=0;
	        
	        Coord3d[] points = new Coord3d[size];
	        Color[]   colors = new Color[size];
	        
	        
	        for(Fitness f:firtPopulationFitness)
			{
	        	points[i] = new Coord3d(f.objective1, f.objective2, f.objective3);
	        	colors[i] = new Color(255,0, 0);
	        	
	        	i++;
			}
	        
	        for(Fitness f:lastPopulationFitness)
			{
	        	points[i] = new Coord3d(f.objective1, f.objective2, f.objective3);
	        	colors[i] = new Color(255,0, 0);
	        	
	        	i++;
			}
	        
	        for(Fitness f:archiveFitness)
			{
	        	points[i] = new Coord3d(f.objective1, f.objective2, f.objective3);
	        	colors[i] = new Color(0,0, 255);
	        	i++;
			}
	        
	        Scatter scatter = new Scatter(points, colors);
	        scatter.setWidth(5);
	    
	        Chart chart = new Chart(Quality.Advanced, "awt");
	        
	        //Chart chart = new Chart("offscreen,400,400");
	        chart.getScene().add(scatter);
	        ChartLauncher.instructions();
	        ChartLauncher.openChart(chart, new Rectangle(800,800), "Test");
	        ChartLauncher.screenshot(chart, OUTPUT_PATH + "/output/multi-objective/" + fileName + ".png");
	        
	        //BufferedImage image = chart.screenshot();
	        //ImageIO.write(image, "PNG", new File(Config.DEFAULT_OUTPUT_PATH + "/output/test.png"));
	  }
	  
	  public static void main(String[] args)
	  {
		  try
		  {
			  if(args.length == 2)
			  {
				  OUTPUT_PATH = args[0];
				  OUTPUT_FILE = args[1];
				  
				  File fOutput = new File(OUTPUT_PATH + "/output");
				  if(!fOutput.exists())
				  {
					  fOutput.mkdir();
					  
				  }
				  
				  File fMultiObjective = new File(OUTPUT_PATH + "/output/multi-objective");
				  if(!fMultiObjective.exists())
				  {
					  fMultiObjective.mkdir();  
				  }
				  
				  
				  System.out.println("-Output Path:" + OUTPUT_PATH);
				  System.out.println("-Output File:" + OUTPUT_FILE);
				  
				  MOOutputAnalyser.readSolution();
				  MOOutputAnalyser.readSolution1();
				  //drawChart();
				 // draw3DPlot();
			  }
			  else
			  {
				  System.out.println("Usage:");
				  System.out.println("MOOutputAnalyser [-Output path] [-Output file]");
			  }
		  }
		  catch(Exception ex)
		  {
			  System.out.println(ex.getMessage());
		  }
	  }
	  
}


