package uow.cmde.transim.transit.outputanalysis;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.util.*;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.JFreeChart;
import java.awt.Font;

/**
 * 
 * @author Vu The Tran
 * @since 21/12/2011
 */
public class ChartHandler {

	/**
	 * CreatePassengerByTimeAndDate
	 * @param stopActivities
	 * @return
	 */
	public static JFreeChart generatePassengerByTimeAndDateChart(IStopActivities stopActivities)
	{
		 JFreeChart chart =null;
		 DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
		 
		 for(IStopActivity stopActivity : stopActivities.getAllStopActivities())
		 {
			 categoryDataset.setValue(stopActivity.getTotalNumberPassenger(),stopActivity.getDate(),stopActivity.getArrivalTime());
		 }
		 
		 
		 chart = ChartFactory.createBarChart
                 ("Passengers by time and day", // Title
                  "Time",              // X-Axis label
                  "Number of passengers",// Y-Axis label
                  categoryDataset,         // Dataset
                  PlotOrientation.VERTICAL,
                  true,                     // Show legend
                  true,
                  false
                 );
    
		 
		 //ValueAxis axis = chart.getCategoryPlot().getRangeAxisForDataset(2);
		 //axis.setLabelAngle(Math.PI / 4.0);
		 
		 
		 return chart;
	}
	
	/**
	 * CreatePassengerOnOffAtEachBusStop
	 * @param stopActivities
	 * @return
	 */
	public static JFreeChart generatePassengerOnOffAtEachBusStopChart(IStopActivities stopActivities)
	{
		 JFreeChart chart =null;
		 DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
		 
		 for(IStopActivity stopActivity : stopActivities.getAllStopActivities())
		 {
			 categoryDataset.setValue(stopActivity.getNumberPassengerOn(),"Boarding",stopActivity.getStop().getStopName());
			 categoryDataset.setValue(stopActivity.getNumberPassengerOff(),"Alighting",stopActivity.getStop().getStopName());
			 
		 }
		 
		 
		 chart = ChartFactory.createLineChart
                ("Passengers boarding and alighting at each bus stop", // Title
                 "Stop",              // X-Axis label
                 "Number of passengers",// Y-Axis label
                 categoryDataset,         // Dataset
                 PlotOrientation.VERTICAL,
                 true,                     // Show legend
                 true,
                 false
                );
   
		 
		 	CategoryPlot plot = (CategoryPlot) chart.getPlot();
		 	plot.setBackgroundPaint(Color.WHITE);
		        
			Stroke[] seriesStrokeArray = new Stroke[3];
			seriesStrokeArray[0] = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
			1.0f, new float[] { 10.0f, 6.0f }, 0.0f);
			seriesStrokeArray[1] = new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
			1.0f, new float[] { 6.0f, 6.0f }, 0.0f);
			
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			renderer.setShapesVisible(true);
			renderer.setDrawOutlines(true);
			renderer.setUseFillPaint(true);
			
			renderer.setSeriesStroke(0, seriesStrokeArray[0]);
			renderer.setSeriesStroke(1, seriesStrokeArray[1]);
		 
		 return chart;
	}
	
	/**
	 * CreatePassengerPerDay
	 * @param stopActivities
	 * @return
	 */
	public static JFreeChart generatePassengerPerDayChart(IStopActivities stopActivities)
	{
		JFreeChart chart =null;
		 DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
		 
		 for(IStopActivity stopActivity : stopActivities.getAllStopActivities())
		 {
			 categoryDataset.setValue(stopActivity.getTotalNumberPassenger(),"Total of passengers",stopActivity.getDayOfWeek());
			 categoryDataset.setValue(stopActivity.getNumberPassengerOn(),"Passengers on",stopActivity.getDayOfWeek());
			 categoryDataset.setValue(stopActivity.getNumberPassengerOff(),"Passengers off",stopActivity.getDayOfWeek());
		 }
		 
		 
		 chart = ChartFactory.createBarChart
                ("Passengers per day", // Title
                 "Day",              // X-Axis label
                 "Number of passengers",// Y-Axis label
                 categoryDataset,         // Dataset
                 PlotOrientation.VERTICAL,
                 true,                     // Show legend
                 true,
                 false
                );
   
		
		 
		 return chart;
	}
	
	
	public static JFreeChart generateHeadwayAdherenceForADayChart(Map<String, IStopActivities> map)
	{
			JFreeChart chart =null;
			
			Object[] keys=map.keySet().toArray();
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			
			int i = 0;
			for(Object o:keys)
			{
				TimeSeries s1 = null;
				IStopActivities tmpActivities = map.get(o.toString());
				s1 = new TimeSeries("Time series" + i, Second.class);
				
				for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
				{
					String arrivalTime = stopActivity.getArrivalTime();
				 	StringTokenizer timeTokenizer = new  StringTokenizer(arrivalTime,":");
				 	
				 	
				 	if(timeTokenizer.countTokens()==3)
				 	{
						int hh = Integer.parseInt(timeTokenizer.nextToken());
						int mm = Integer.parseInt(timeTokenizer.nextToken());
						int ss = Integer.parseInt(timeTokenizer.nextToken());
					
					 	s1.addOrUpdate(new Second(ss,mm, hh, 7, 12, 2003), Integer.parseInt(stopActivity.getStop().getStopCode()));
				 	}
				 	
				 	//System.out.println(stopActivity.getTrip() + "@" + arrivalTime);
				}
				
				if(s1!=null)
				{
					
					dataset.addSeries(s1);
				}
				
				i++;
				
			}
			
		
		    chart = ChartFactory.createTimeSeriesChart(
		            "Headway adherence",
		            "Time", 
		            "Stop sequence",
		            dataset,
		            false,
		            true,
		            false
		     );
  
		    XYPlot plot = (XYPlot) chart.getPlot();
		 	plot.setBackgroundPaint(Color.WHITE);
		        
	
		 	return chart;
	}
	
	public static JFreeChart generateWaitTimeIncrementChart(Map<String, IStopActivities> map)
	{
		JFreeChart chart =null;
		
		DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
		String labelInVehicle = "In-vehicle Time";
		String labelTravelTime = "Vehicle Travel Time";
		
		Object[] keys=map.keySet().toArray();
	
		
		int tripIndex  =1;
		
		for(Object o:keys)
		{
			IStopActivities tmpActivities = map.get(o.toString());
			
			double totalActualHeadwayInSecond = 0;
			double totalForecastHeadwayInSecond = 0;
			double totalActualInVehicleTime = 0;
			double totalForecastInVehicleTime = 0;
			
			for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
			{
			
				int numberPassengerOnVehicle = stopActivity.getNumberPassengerOnVehicle();
				String departureTime = stopActivity.getDepartureTime();
				String forecastDepartureTime = stopActivity.getForecastDepartureTime();
				String lastDepartureTime = stopActivity.getLastDepartureTime();
				
				
				
				if(lastDepartureTime!=null && !lastDepartureTime.equals(""))
				{
					//Calculate wait time
					double actualHeadwayInSecond = TimeConverter.convertTimeToSecond(departureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
					double forecastHeadwayInSecond = TimeConverter.convertTimeToSecond(forecastDepartureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
					
					
					//Calculate invehicle 
					totalActualHeadwayInSecond += actualHeadwayInSecond;
					totalForecastHeadwayInSecond +=forecastHeadwayInSecond;
					
					totalActualInVehicleTime += actualHeadwayInSecond*numberPassengerOnVehicle;
					totalForecastInVehicleTime += forecastHeadwayInSecond*numberPassengerOnVehicle;
					
				}	
			}
			
			double reductionInTravelTime = (1.0 - totalForecastHeadwayInSecond/totalActualHeadwayInSecond)*(100);
			double reductionInVehicleTime = (1.0 - totalForecastInVehicleTime/totalActualInVehicleTime)*(100);
			
			categoryDataset.setValue(reductionInTravelTime, labelTravelTime, "Trip " + tripIndex);
			categoryDataset.setValue(reductionInVehicleTime, labelInVehicle, "Trip " + tripIndex);
			
			tripIndex ++;
		}
		
		chart = ChartFactory.createBarChart3D
                ("Wai time", 
                 "Trip",              
                 "Increment (%)",
                 categoryDataset,         
                 PlotOrientation.VERTICAL,
                 true,                     
                 true,
                 false
                );
		
		
		//CategoryPlot plot = (CategoryPlot) chart.getPlot();
		//BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
		
	    return chart;
			
	}
	
	public static JFreeChart generateWaitTimeReductionChart(Map<String, IStopActivities> map)
	{
		JFreeChart chart =null;
		
		DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
		String labelTravelTime = "Wait time";
		
		Object[] keys=map.keySet().toArray();
	
		
		int tripIndex  =1;
		
		for(Object o:keys)
		{
			IStopActivities tmpActivities = map.get(o.toString());
			
			double totalPassengerWaitTime = 0;
			
			for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
			{
			
				int numberPassengerOnVehicle = stopActivity.getNumberPassengerOn();
				int numberPassengerLeftOver= stopActivity.getNumberPassengerLeftOver();
				String departureTime = stopActivity.getDepartureTime();
				String lastDepartureTime = stopActivity.getLastDepartureTime();
				
				
				
				if(lastDepartureTime!=null && !lastDepartureTime.equals(""))
				{
					//Calculate wait time
					double actualHeadwayInSecond = TimeConverter.convertTimeToSecond(departureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
					
					totalPassengerWaitTime += actualHeadwayInSecond* (numberPassengerOnVehicle + numberPassengerLeftOver);
					
					
				}	
			}
			
			
			categoryDataset.setValue(totalPassengerWaitTime, labelTravelTime, o.toString());
			
			tripIndex ++;
		}
		
		chart = ChartFactory.createBarChart3D
                ("Passenger Wai time at bus stop", 
                 "Trip",              
                 "Reduction (%)",
                 categoryDataset,         
                 PlotOrientation.VERTICAL,
                 true,                     
                 true,
                 false
                );
		
	    return chart;
			
	}
		
		public static JFreeChart generatePassengerWaitTimeChart(Map<String, IStopActivities> map)
		{
			JFreeChart chart =null;
			
			DefaultCategoryDataset  categoryDataset = new DefaultCategoryDataset();
			String labelWaitTime = "Wait Time";
			String labelExpectedWaitTime = "Expected Wait Time";
			
			Object[] keys=map.keySet().toArray();
			TransitParameters transitParameters = new TransitParameters();
			
			
			int tripIndex  =1;
			
			for(Object o:keys)
			{
				IStopActivities tmpActivities = map.get(o.toString());
				double totalActualWaitTime = 0;
				
				int k = 0;
				for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
				{
					
					String departureTime = stopActivity.getDepartureTime();
					String lastDepartureTime = stopActivity.getLastDepartureTime();
					
					if(lastDepartureTime!=null && !lastDepartureTime.equals(""))
					{
						//Calculate wait time
						double actualHeadwayInSecond = TimeConverter.convertTimeToSecond(departureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
						
						if(actualHeadwayInSecond < AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND*2)
						{
							double actualWaitTime = (actualHeadwayInSecond/60)/2 + AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_MINUTE/((actualHeadwayInSecond/60)*2);
							
							totalActualWaitTime +=actualWaitTime;
							
							k++;
						}
					}	
				}
				
				
				double meanActualWaiTime = totalActualWaitTime/k;
				
				categoryDataset.setValue(transitParameters.getExpectedWaitTime(), labelExpectedWaitTime, "Trip " + tripIndex);
				categoryDataset.setValue(meanActualWaiTime, labelWaitTime, "Trip " + tripIndex);
				
				tripIndex ++;
				
			}
		
		chart = ChartFactory.createLineChart
                ("Wai time", 
                 "Trip",              
                 "Time (minute)",
                 categoryDataset,         
                 PlotOrientation.VERTICAL,
                 true,                     
                 true,
                 false
                );
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		//plot.setRangeGridlinePaint(Color.white);
	        
		Stroke[] seriesStrokeArray = new Stroke[3];
		seriesStrokeArray[0] = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		1.0f, new float[] { 10.0f, 6.0f }, 0.0f);
		seriesStrokeArray[1] = new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
		1.0f, new float[] { 6.0f, 6.0f }, 0.0f);
		
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setUseFillPaint(true);
		//renderer.setFillPaint(Color.white);
		
		//plot.setSeriesStroke(seriesStrokeArray);
		
		renderer.setSeriesStroke(0, seriesStrokeArray[0]);
		renderer.setSeriesStroke(1, seriesStrokeArray[1]);
		
		return chart;

	}
	
	public static JFreeChart generateServiceReliabilityMeasureChart(Map<String, IStopActivities> map)
	{
		 JFreeChart chart =null;
		 DefaultCategoryDataset  categoryDatasetHeadway = new DefaultCategoryDataset();
		 DefaultCategoryDataset  categoryDatasetPassengerComfort = new DefaultCategoryDataset();
		 DefaultCategoryDataset  categoryDatasetPassengerWaitTime = new DefaultCategoryDataset();

		
		 
		 Object[] keys=map.keySet().toArray();
		 for(Object o:keys)
		 {
				IStopActivities tmpActivities = map.get(o.toString());
				

				double k = 0, k1 =0;
				int totalPassengerComfortLevel = 0, k2 = 0;
				int seatedCapacity = 50, standingCapacity = 20;
				double totalPassengerWaitTime = 0;
				
				for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
				{
					
					String departureTime = stopActivity.getDepartureTime();
					String lastDepartureTime = stopActivity.getLastDepartureTime();
					int numberPassengerOnVehicle = stopActivity.getNumberPassengerOnVehicle() + stopActivity.getNumberPassengerOn();
					int passengerComfortLevel = 6;
					
					if((seatedCapacity ) >= 2*numberPassengerOnVehicle)  passengerComfortLevel = 1;
					else if (seatedCapacity > numberPassengerOnVehicle)  passengerComfortLevel = 2;
					else if (seatedCapacity == numberPassengerOnVehicle)  passengerComfortLevel = 3;
					else if (standingCapacity >= 2* (numberPassengerOnVehicle - seatedCapacity))  passengerComfortLevel = 4;
					else if (standingCapacity > numberPassengerOnVehicle - seatedCapacity)  passengerComfortLevel = 5;
					else if (standingCapacity  == (numberPassengerOnVehicle - seatedCapacity)) passengerComfortLevel = 6;
					//else passengerComfortLevel = 6;
					
					totalPassengerComfortLevel += passengerComfortLevel;
					
					if(lastDepartureTime!=null && !lastDepartureTime.equals(""))
					{
						//Calculate wait time
						double actualHeadwayInSecond = TimeConverter.convertTimeToSecond(departureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
						
						double difference = Math.abs(actualHeadwayInSecond - AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_SECOND);
						
						//System.out.println("" + difference/60);
						//System.out.println("" + actualHeadwayInSecond/60);
						if(difference<5*60)
						{
							k1++;
						}
						
						totalPassengerWaitTime += (actualHeadwayInSecond /60) * (stopActivity.getNumberPassengerOn() + stopActivity.getNumberPassengerLeftOver()); 
						
						k++;
						
					}	
					
					k2++;
				}
				
				
				double headwayReliability = k1/k;
				int averagePassengerComfort = totalPassengerComfortLevel / k2;
				int averagePassengerWaitTime = (int)totalPassengerWaitTime / (int)k;
				
				
				categoryDatasetHeadway.setValue(headwayReliability,"Headway",o.toString());
				categoryDatasetPassengerComfort.setValue(averagePassengerComfort,"Passenger Comfort",o.toString());
				categoryDatasetPassengerWaitTime.setValue(averagePassengerWaitTime,"Passenger Wait time",o.toString());
		 }
		 
		
		 NumberAxis rangeAxis1 = new NumberAxis("Headway reliability");
		 //rangeAxis1.setAutoRangeIncludesZero(false);
		 LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
		 renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		 CategoryPlot subplot1 = new CategoryPlot(categoryDatasetHeadway, null, rangeAxis1, renderer1);
		 subplot1.setDomainGridlinesVisible(true);
		 
		 NumberAxis rangeAxis2 = new NumberAxis("Comfort level");
		 rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		 //rangeAxis2.setAutoRangeIncludesZero(false);
		 LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		 renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		 CategoryPlot subplot2 = new CategoryPlot(categoryDatasetPassengerComfort, null, rangeAxis2, renderer2);
		 subplot2.setDomainGridlinesVisible(true);
		 
		 NumberAxis rangeAxis3 = new NumberAxis("Wait time");
		 rangeAxis3.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		 //rangeAxis2.setAutoRangeIncludesZero(false);
		 LineAndShapeRenderer renderer3 = new LineAndShapeRenderer();
		 renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		 CategoryPlot subplot3 = new CategoryPlot(categoryDatasetPassengerWaitTime, null, rangeAxis3, renderer3);
		 subplot3.setDomainGridlinesVisible(true);
		 
		 CategoryAxis domainAxis = new CategoryAxis("stop");
		 CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
		 plot.add(subplot1, 3);
		 plot.add(subplot2, 3);
		 plot.add(subplot3, 3);
		 
		 chart = new JFreeChart(
				 "Service reliability",
				 new Font("SansSerif", Font.BOLD, 12),
				 plot,
				 true
				 );
		 
		/*
		 chart = ChartFactory.createBarChart3D
				 		    ("Service Reliability", // Title
			                "Stop",              // X-Axis label
			                "reliability",// Y-Axis label
			                categoryDataset,         // Dataset
			                PlotOrientation.VERTICAL,
			                true,                     // Show legend
			                true,
			                false
			      );
			      */
		 
		 
	        /*
		 chart = ChartFactory.createLineChart
               ("Service Reliability", // Title
                "Stop",              // X-Axis label
                "reliability",// Y-Axis label
                categoryDatasetHeadway,         // Dataset
                PlotOrientation.VERTICAL,
                true,                     // Show legend
                true,
                false
               );
  
		 
		 	CategoryPlot plot = (CategoryPlot) chart.getPlot();
		 	plot.setBackgroundPaint(Color.WHITE);
		        
			Stroke[] seriesStrokeArray = new Stroke[3];
			seriesStrokeArray[0] = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
			1.0f, new float[] { 10.0f, 6.0f }, 0.0f);
			//seriesStrokeArray[1] = new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
			//1.0f, new float[] { 6.0f, 6.0f }, 0.0f);
			
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			renderer.setShapesVisible(true);
			renderer.setDrawOutlines(true);
			renderer.setUseFillPaint(true);
			
			renderer.setSeriesStroke(0, seriesStrokeArray[0]);
			//renderer.setSeriesStroke(1, seriesStrokeArray[1]);
	      */
	        
		 return chart;
	}
		
		
	/**
	 * saveChart
	 * @param chart
	 * @param filePath
	 */
	public static void saveChart(JFreeChart chart, String filePath)
    {
        
        try {
           
            ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Problem occurred creating chart.");
        }
    }
   
    /**
     * getChartPanel
     * @param chart
     * @return
     */
    public static ChartPanel getChartPanel(JFreeChart chart)
    {
    	if(chart!=null)
    	{
    		return (new ChartPanel(chart));
    	}
    	
    	return null;
    }
}
