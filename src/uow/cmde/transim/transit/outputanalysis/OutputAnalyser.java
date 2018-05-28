package uow.cmde.transim.transit.outputanalysis;

import com.csvreader.CsvReader;

import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.util.AppConfig;
import uow.cmde.transim.util.TimeConverter;

import java.io.File;
import java.util.*;

public class OutputAnalyser {

	public StopActivities stopActivities;
	private String selectedDate;
	private String prefix;
	private String prefix1;
	private String outputFolder;
	private String outputFile;
	
	private String filteredByDate = "Date";
	private String filteredByTrip = "Trip";
	private String filteredByDistinctTrip = "DistinctTrip";
	private String filteredByStopCode = "StopCode";
	private String filteredByDayOfWeek = "DayOfWeek";
	
	public OutputAnalyser(String outputFolder, String outputFile, String selectedDate)
	{
		this.outputFolder = outputFolder + "/output/analysis";  
		this.outputFile = outputFile;
		this.selectedDate = selectedDate;
		this.prefix1 = "/" + selectedDate.replace("/", "_") + "_";
		
	}
	
	private void doReport(String filePath, String prefix) throws Exception
	{
		this.prefix = this.prefix1 + prefix;
		read(filePath);
		reportPassengerOnOffAtEachBusStop();
		reportPassengerPerDay();
		reportHeadwayAdherence();
		reportWaitTime();
		reportWaitTimeReduction();
		reportServiceReliability();
	}

	private void read(String inputPath) throws Exception
	{
		stopActivities = new StopActivities();
		
		CsvReader csvReader= new CsvReader(inputPath);
		csvReader.readHeaders();
		
		while (csvReader.readRecord())
		{
			StopActivity stopActivity = new StopActivity();
			
			int numberPassengerOn = Integer.parseInt(csvReader.get("passenger_on"));
			int numberPassengerOff = Integer.parseInt(csvReader.get("passenger_off"));
			int numberPassengerLeftOver = Integer.parseInt(csvReader.get("passenger_left_over"));
			int numberPassengerOnVehicle = Integer.parseInt(csvReader.get("current_passenger_on"));
			
			stopActivity.setNumberPassengerOn(numberPassengerOn);
			stopActivity.setNumberPassengerOff(numberPassengerOff);
			stopActivity.setVehicleId(csvReader.get("vehicle"));
			stopActivity.setDate(csvReader.get("date"));
			stopActivity.setDayOfWeek(csvReader.get("day_of_week"));
			stopActivity.setArrivalTime(csvReader.get("arrival_time"));
			stopActivity.setLastDepartureTime(csvReader.get("last_departure_time"));
			stopActivity.setDepartureTime(csvReader.get("departure_time"));
			stopActivity.setForecastDepartureTime(csvReader.get("forecast_departure_time"));
			stopActivity.setTrip(csvReader.get("trip_number"));
			stopActivity.setNumberPassengerLeftOver(numberPassengerLeftOver);
			stopActivity.setNumberPassengerOnVehicle(numberPassengerOnVehicle);
			
			Stop stop = new Stop();
			stop.setStopCode(csvReader.get("stop_code"));
			
			stopActivity.setStop(stop);
			
			stopActivities.addStopActivity(stopActivity);
			
		}
		
		csvReader.close();
	}
	
	private Map<String, IStopActivities> doFilter(String filter)
	{
		Map<String, IStopActivities> map = new HashMap<String, IStopActivities>();
		
		for(IStopActivity stopActivity:stopActivities.getAllStopActivities())
		{
			    String key = "";
			    
			    if(filter.equals(this.filteredByDate))
			    {
			    	key = stopActivity.getDate();
			    }
			    else if(filter.equals(this.filteredByTrip))
			    {
			    	key = stopActivity.getTrip();
					key = key.substring(8, key.length());
			    }
			    else if(filter.equals(this.filteredByDistinctTrip))
			    {
			    	key = stopActivity.getTrip();
			    }
			    else if(filter.equals(this.filteredByDayOfWeek))
			    {
			    	key = stopActivity.getDayOfWeek();
			    }
			    else if(filter.equals(this.filteredByStopCode))
			    {
			    	key = stopActivity.getStop().getStopCode();
			    }
				
				if(map.get(key) == null)
				{
					map.put(key, new StopActivities());
				}
				
				map.get(key).addStopActivity(stopActivity);
				
		}
		
		return map;
	}
	
	public void reportPassengerOnOffAtEachBusStop() 
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByStopCode);
		
		Object[] keys=map.keySet().toArray();
		
		StopActivities myStopActivities = new StopActivities();
		for(Object o:keys)
		{
			IStopActivities tmpActivities = map.get(o.toString());
			int numberPassengerOn = 0;
			int numberPassengerOff = 0;
			for(IStopActivity s:tmpActivities.getAllStopActivities())
			{
				numberPassengerOn +=s.getNumberPassengerOn();
				numberPassengerOff +=s.getNumberPassengerOff();
			}
			
			StopActivity sa=new StopActivity();
			sa.setNumberPassengerOn(numberPassengerOn);
			sa.setNumberPassengerOff(numberPassengerOff);
			Stop stop = new Stop();
			stop.setStopName(o.toString());
			sa.setStop(stop);
			
			myStopActivities.addStopActivity(sa);
			
			
		}
		
		ChartHandler.saveChart(ChartHandler.generatePassengerOnOffAtEachBusStopChart(myStopActivities),outputFolder + prefix + "passerenger_perstop.jpg");
	}
	
	public void reportPassengerPerDay()
	{
		
		Map<String, IStopActivities> map = doFilter(this.filteredByDayOfWeek);
		
		Object[] keys=map.keySet().toArray();
		
		StopActivities myStopActivities = new StopActivities();
		for(Object o:keys)
		{
			IStopActivities tmpActivities = map.get(o.toString());
			int numberPassengerOn = 0;
			int numberPassengerOff = 0;
			for(IStopActivity s:tmpActivities.getAllStopActivities())
			{
				numberPassengerOn +=s.getNumberPassengerOn();
				numberPassengerOff +=s.getNumberPassengerOff();
			}
			
			StopActivity sa=new StopActivity();
			sa.setNumberPassengerOn(numberPassengerOn);
			sa.setNumberPassengerOff(numberPassengerOff);
			sa.setDayOfWeek(o.toString());
			
			myStopActivities.addStopActivity(sa);
			
			
		}
		
		ChartHandler.saveChart(ChartHandler.generatePassengerPerDayChart(myStopActivities),outputFolder + prefix + "passerenger_per_day.jpg");
		
	}
	
	
	public void reportHeadwayAdherence()
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByDistinctTrip);
		ChartHandler.saveChart(ChartHandler.generateHeadwayAdherenceForADayChart(map),outputFolder + prefix + "headway.jpg");
	}
	
	public void reportWaitTime()
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByTrip);
		ChartHandler.saveChart(ChartHandler.generateWaitTimeIncrementChart(map),outputFolder + prefix + "waittime.jpg");
		ChartHandler.saveChart(ChartHandler.generatePassengerWaitTimeChart(map),outputFolder + prefix + "passengerwaittime.jpg");
	}
	
	public void reportWaitTimeReduction()
	{
		Map<String, IStopActivities> map =doFilter(this.filteredByDate);
		ChartHandler.saveChart(ChartHandler.generateWaitTimeReductionChart(map),outputFolder + prefix + "passengerwaittimereduction.jpg");
		
	}
	
	public void reportServiceReliability()
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByStopCode);
		ChartHandler.saveChart(ChartHandler.generateServiceReliabilityMeasureChart(map),outputFolder + prefix + "servicereliability.jpg");
	}
	
	public void validateData()
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByStopCode);
		ModelValidation.validateHeadwayAdherence(map, outputFolder + prefix + "validation_headway.csv");
	}
	
	public void validateTripTime()
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByTrip);
		ModelValidation.validateTripTime(map, outputFolder + prefix + "validation_trip.csv");
	}
	
	public void validateScheduleAdherence()
	{
		Map<String, IStopActivities> map = doFilter(this.filteredByStopCode);
		ModelValidation.validateScheduleAdherence(map, outputFolder + prefix + "validation_schedule.csv");
	}
	
	
	public static void main(String[] args) {
		
		try
		{
			
			if(args.length == 3)
			{
			
				String outputPath = args[0];
				String outputFile = args[1];
				String date = args[2];
				
				System.out.println("-Output path:" + outputPath);
				System.out.println("-Output file:" + outputFile);
				System.out.println("-Filter date:" + date);
				
				System.out.println("Handling output files...");
				String amFilePath = outputPath + "/" + outputFile.replace(".csv", "") + "_am.csv";
				String pmFilePath = outputPath + "/" + outputFile.replace(".csv", "") + "_pm.csv";
				
				File fileAM = new File(amFilePath);
				File filePM = new File(pmFilePath);
				
				File fOutput = new File(outputPath + "/output");
				if(!fOutput.exists())
				{
					  fOutput.mkdir();
					  
				}
				  
				File fAnalysis= new File(outputPath + "/output/analysis");
				if(!fAnalysis.exists())
				{
					  fAnalysis.mkdir();  
				}
				  
				if(fileAM.exists())
				{
					fileAM.delete();
				}
				
				if(filePM.exists())
				{
					filePM.delete();
				}
				
				
				if(!fileAM.exists() && !filePM.exists())
				{
					PeakHour.splitAmPmData(date,outputPath,outputFile,amFilePath,pmFilePath);
				}
				
				
				
				System.out.println("Generating reports and analysis...");
				
				OutputAnalyser outputAnalysis = new OutputAnalyser(outputPath,outputFile,date);
				outputAnalysis.doReport(amFilePath, "_AM");
				outputAnalysis.doReport(pmFilePath, "_PM");
				
			
				System.out.println("Validating data...");
				outputAnalysis.validateData();
				outputAnalysis.validateTripTime();
				outputAnalysis.validateScheduleAdherence();
				
				System.out.println("Done!");
				
				
			}
			else
			{
				System.out.println("Usage:");
				System.out.println("OutputAnalyser [Output Path] [Output File] [Filter Date]");
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Cannot found output file/invalid file");
			System.out.println(ex.getMessage());
		}
		
	} 
}
