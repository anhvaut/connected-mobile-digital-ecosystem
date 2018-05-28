package uow.cmde.transim.transit.controller;


import uow.cmde.transim.transit.demandmodel.impl.HistoryPassengerDemand;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.util.AppConfig;
import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.util.AppHandler;
import uow.cmde.transim.multiobjective.controller.*;
import uow.cmde.transim.transit.strategies.*;

import com.csvreader.CsvWriter;
import java.io.*;



/**
 * 
 * @author Vu The Tran
 * @since 12/01/2011
 */
public class TransitHandler {
	
	/**
	 * initalizeRunningVehicle
	 * @param routes
	 * @return
	 */
	

	private static String outputFolder;
	private static String transportPlan;
	
	public static void initalizeTransitNetwork(String oFolder, String tPlan)
	{

		outputFolder = oFolder;
		transportPlan = tPlan;
		
	}
	
	public static IActiveVehicles initalizeRunningVehicle(IRoutes routes)
	{
		IActiveVehicles vehicles = null;
		
		try
		{
			vehicles = AppHandler.readVehicle(transportPlan, routes);
					
			int vehicleIndex = 0;
			for(IActiveVehicle vehicle:vehicles.getAllVehicles())
			{
				vehicle.setVehicleIndex(vehicleIndex);
				vehicle.setVehicleId(vehicleIndex);
				vehicleIndex++;
			}
		}
		catch(Exception ex)
		{
			System.out.println("initalizeRunningVehicle:" + ex.getMessage());
		}
		
		return vehicles;
	}
	
	
	
	
	/**
	 * printRunningVehicleInfo
	 * @param vehicles
	 * @param currentTime
	 * @return
	 */
	public static String printRunningVehicleInfo(IActiveVehicles vehicles, String currentTime)
	{
		String vehicleInfo = "";
		for(IActiveVehicle vehicle:vehicles.getAllVehicles())
		{
			if(vehicle!=null)
			{
				vehicleInfo += " .......... \n";
				vehicleInfo += "Name:" + vehicle.getVehicleName() + "\n";
				vehicleInfo += "travelled(km):" + vehicle.getTravelledMeter() + "\n";
				vehicleInfo += "trip no:" + vehicle.getCurrentTripNumber() + "\n";
				vehicleInfo += "passengers:" + vehicle.getCurrentPassengerOn() + "\n";
				
			}
		}
		
		return vehicleInfo;
	}
	
	/**
	 * initalizePassengerDemand
	 * @param routes
	 */
	public static void initalizePassengerDemand(IRoutes routes) throws Exception
	{
		HistoryPassengerDemand.initalizePassengerDemand(routes);
	}
	
	
	public static void updatePassengerDemand(IRoutes routes, String dayOfWeek,String timePoint)
	{
		HistoryPassengerDemand.updatePassengerDemandPerTimeAndDay(routes, dayOfWeek, timePoint);
	}
	
	/**
	 * printPassengerDemandInfo
	 * @param routes
	 * @return
	 */
	public static String printPassengerDemandInfo(IRoutes routes)
	{
		String passengerInfo = "";
		if( routes!=null)
		{	
			for(IRoute route:routes.getAllRoutes())
			{
				for(IStop stop:route.getSequenceStops().getAllStops())
				{
					passengerInfo +=stop.getStopName() + ":" +  stop.getPassengerWaiting() +"\n";
					
				}
			}
		}
		
		return passengerInfo;
	}
	
	/**
	 * actionPerformedAtStop
	 * @param vehicles
	 * @param currentTime
	 * @param routes
	 */
	public static void actionPerformedAtStop(IRoutes routes,IActiveVehicles vehicles, String date, String dayOfWeek, String currentTime)
	{
		//reset
		if(currentTime.equals("12:00:00") || currentTime.equals("24:00:00"))
		{
			for(IRoute route:routes.getAllRoutes())
			{
				for(IStop stop:route.getSequenceStops().getAllStops())
				{
					stop.reset();
				}
			}
			
			for(IActiveVehicle vehicle:vehicles.getAllVehicles())
			{
				vehicle.setBackToGarage();
			}
			
		}
		

		for(IActiveVehicle vehicle:vehicles.getAllVehicles())
		{
			
			
			if(vehicle!=null  && vehicle.isStartRunning())
			{
				
				if(vehicle.getLastStopIndex() < vehicle.getRoute().getNumberOfStop() - 1)
				{
					int nextStopIndex = vehicle.getLastStopIndex() + 1;
					IStop aStop=vehicle.getRoute().getSequenceStops().getStop(nextStopIndex);
					
					int passengerWaiting = aStop.getPassengerWaiting();
					if(passengerWaiting == -1) passengerWaiting = 0;
				
					TransitParameters tParameters = new TransitParameters();
					tParameters.setNumberPassengerWait(passengerWaiting);
					tParameters.setNumberPassengerOnVehicle(vehicle.getCurrentPassengerOn());
					tParameters.setStandingCapacity(vehicle.getStandingCapacity());
					tParameters.setSeatedCapacity(vehicle.getSeatCapacity());
					
					TransitFormular.calculate(tParameters);
					
					String actualDepartureTimeOfPredecessorVehicle = aStop.getLastDepartureTime();
					String forecastArrivalTime = TransitFormular.calculateForecastArrivalTime(vehicle.getDistanceToNextStopInMeter()/1000, currentTime);
					String forecastDepartureTime = TransitFormular.calculateDepartureTime(tParameters.getDwellTime(),forecastArrivalTime);
					tParameters.setForecastHeadwayInMinute(TransitFormular.calculateHeadwayInMinute(actualDepartureTimeOfPredecessorVehicle , forecastDepartureTime));
					
					TransitParameters.addOrUpdateTransitParameters(vehicle.getVehicleName(), tParameters);
				}
				
				int stopSequence = vehicle.isCommingStop();
				if(stopSequence!=-1 )
				{
					
					if(!vehicle.isWaitAtStop() && stopSequence <= vehicle.getRoute().getNumberOfStop())
					{
						int stopIndex = stopSequence -1;
						
						IStop aStop = vehicle.getRoute().getSequenceStops().getStop(stopIndex);
						
						aStop.addWaitingVehicle(vehicle.getVehicleIndex());
						
						
						if(aStop.getNumberOfWaitingVehicle() >1)
						{
							for(IActiveVehicle wv:vehicles.getAllVehicles())
							{
								if(!wv.isStartRunning() && wv.isBackToGarage())
								{
									aStop.removeWaitingVehicle(wv.getVehicleIndex());
								}
							}
						}
						
						//System.out.println(aStop.getNumberOfWaitingVehicle());
						
						//if(vehicle.getVehicleName().equals("vehicle4"))
						//{
						// System.out.println("ok");
						//}
						
						if(aStop.isFirstWaitingVehicle(vehicle.getVehicleIndex()))
						{
							pickupPassenger(vehicles,vehicle,date,dayOfWeek,currentTime,stopIndex);
						}
						else
						{
							vehicle.setPause(true);
						}
					}
					
				}
				
				else if(vehicle.isWaitAtStop())
				{
					int checkTime = TimeConverter.convertTimeToSecond(vehicle.getStopActivity().getDepartureTime());
					
					if(TimeConverter.convertTimeToSecond(currentTime) >=checkTime )
					{
						
						vehicle.setWaitAtStop(false);
						
						IStop aStop = vehicle.getRoute().getSequenceStops().getStop(vehicle.getWaitingStopIndex());
						aStop.removeFirstWaitingVehicle();
						
						if(aStop.hasVehicleWaiting())
						{
							IActiveVehicle holdingVehicle = vehicles.getAllVehicles().get(aStop.getFirstWaitingVehicle());
							holdingVehicle.setPause(false);
							pickupPassenger(vehicles,holdingVehicle,date,dayOfWeek,currentTime,vehicle.getWaitingStopIndex());
						}
						
						if(vehicle.getStopActivity()!=null)
						{
							writeStopActivitiesToFile(vehicle,vehicle.getStopActivity());
						}
					}
					
				}
				
			}
			
			//else if (!vehicle.isStartRunning()){
			//		aStop.removeWaitingVehicle(vehicle.getVehicleIndex());
			//}
			
		}
	}
	
	/**
	 * 
	 * @param vehicles
	 * @param vehicle
	 * @param date
	 * @param dayOfWeek
	 * @param currentTime
	 * @param stopIndex
	 */
	public static void pickupPassenger(IActiveVehicles vehicles, IActiveVehicle vehicle, String date, String dayOfWeek, String currentTime, int stopIndex)
	{
		
		vehicle.setWaitAtStop(true);
		vehicle.setPause(false);
		vehicle.setWaitingStopIndex(stopIndex);
		
		IStop aStop = vehicle.getRoute().getSequenceStops().getStop(stopIndex);
	
		int passengerWaiting = aStop.getPassengerWaiting();
		if(passengerWaiting == -1) passengerWaiting = 0;
		
		TransitParameters tParameters = new TransitParameters();
		tParameters.setNumberPassengerWait(passengerWaiting);
		tParameters.setNumberPassengerOnVehicle(vehicle.getCurrentPassengerOn());
		tParameters.setTotalVehicleCapacity(vehicle.getTotalCapacity());
		tParameters.setStandingCapacity(vehicle.getStandingCapacity());
		tParameters.setSeatedCapacity(vehicle.getSeatCapacity());
		TransitFormular.calculate(tParameters);
		
		vehicle.setCurrentPassengerOn(tParameters.getForecastNumberPassengerOnVehicle());
		vehicle.setDwellTime(tParameters.getDwellTime());
		aStop.setPassengerWaiting(tParameters.getForecastNumberPassengerLeftOver());
	
		//System.out.println("passenger on:" + tParameters.getForecastNumberPassengerOnVehicle());
		
		String actualDepartureTimeOfPredecessorVehicle = "";
		/*
		if(vehicle.getVehicleIndex()>0)
		{
			
			actualDepartureTimeOfPredecessorVehicle = vehicles.getAllVehicles().get(vehicle.getVehicleIndex() -1).getDepartureTime(stopIndex);
			
		}*/
		
		actualDepartureTimeOfPredecessorVehicle = aStop.getLastDepartureTime();
		
		String forecastDepartureTime = TransitFormular.calculateDepartureTime(tParameters.getDwellTime(),currentTime);
		String actualDepartureTime = forecastDepartureTime;
		
		tParameters.setForecastHeadwayInMinute(TransitFormular.calculateHeadwayInMinute(actualDepartureTimeOfPredecessorVehicle , forecastDepartureTime));
		//TransitFormular.calculate(tParameters);
		TransitParameters.addOrUpdateTransitParameters(vehicle.getVehicleName(), tParameters);
		
		
		

		//==============================================================
		// Begin: Multi objective strategies
		//==============================================================
		
		
		//MOPSOParameters.problem = new ServiceReliabilityEvaluation();
		
		if(AppConfig.TRANSIT_USING_STRATEGY && 
				(AppConfig.MO_ALGORITHM.equals("ea") || 
				 AppConfig.MO_ALGORITHM.equals("rs") ||
				 AppConfig.MO_ALGORITHM.equals("sa")))
		{
			AppConfig.MO_NUMBER_OF_DIMENSIONS  = vehicles.getNumberActiveVehicle();
	        //MOPSO mopso = new MOPSO();
	        //mopso.run();
			
			ServiceReliabilityOptimizer srOptimizer = new ServiceReliabilityOptimizer();
			srOptimizer.setAlgorithm(AppConfig.MO_ALGORITHM);
			srOptimizer.setShowGUI(false);
			srOptimizer.run();
		
		}
		if(AppConfig.TRANSIT_USING_STRATEGY && AppConfig.MO_ALGORITHM.equals("holding")) 
		{
			// Holding strategies
			if(!actualDepartureTimeOfPredecessorVehicle.equals(""))
			{
				
				HoldingHandler holdingHandler = new HoldingHandler(tParameters);
				if(holdingHandler.twoStopControlPoint(stopIndex))
				{
					holdingHandler.setForecastDepartureTime(forecastDepartureTime);
					holdingHandler.setActualArrivalTimeOfPredecessorVehicle(actualDepartureTimeOfPredecessorVehicle);
					
					actualDepartureTime =holdingHandler.oneHeadwayBasedHolding();					
					
				}
			}
		}
		
        
        
        //==============================================================
        // End: Multi objective strategies
      	//==============================================================
	
		StopActivity stopActivity = new StopActivity();
		stopActivity.setArrivalTime(currentTime);
		stopActivity.setDepartureTime(actualDepartureTime);
		stopActivity.setForecastDepartureTime(forecastDepartureTime);
		stopActivity.setLastDepartureTime(actualDepartureTimeOfPredecessorVehicle);
		stopActivity.setStop(aStop);
		stopActivity.setDate(date);
		stopActivity.setDayOfWeek(dayOfWeek);
		stopActivity.setNumberPassengerOn(tParameters.getForecastNumberPassengerBoarding());
		stopActivity.setNumberPassengerOff(tParameters.getForecastNumberPassengerAlighting());
		stopActivity.setNumberPassengerLeftOver(tParameters.getForecastNumberPassengerLeftOver());
		vehicle.setStopActivity(stopActivity);
		vehicle.addDepartureTime(stopIndex, actualDepartureTime);
		aStop.setLastDepartureTime(actualDepartureTime);
	}
	
	/**
	 * writeStopActivitiesToFile
	 * @param stopActivities
	 */
	public static void writeStopActivitiesToFile(IActiveVehicle vehicle,IStopActivity stopActivity)
	{
		String outputFile = AppConfig.OUTPUT_PATH + "/" + AppConfig.OUTPUT_FILE; 
		
		File outputFolder = new File(AppConfig.OUTPUT_PATH );
		if(!outputFolder.exists())
		{
			outputFolder.mkdir();
		}
		
		boolean alreadyExists = new File(outputFile).exists();
			
		try {
			
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
			if (!alreadyExists)
			{
				csvOutput.write("vehicle");
				csvOutput.write("stop_code");
				csvOutput.write("date");
				csvOutput.write("day_of_week");
				csvOutput.write("arrival_time");
				csvOutput.write("departure_time");
				csvOutput.write("forecast_departure_time");
				csvOutput.write("last_departure_time");
				csvOutput.write("trip_number");
				csvOutput.write("current_passenger_on");
				csvOutput.write("passenger_on");
				csvOutput.write("passenger_off");
				csvOutput.write("passenger_left_over");
				csvOutput.endRecord();
			}
		
			csvOutput.write(vehicle.getVehicleName());
			csvOutput.write(stopActivity.getStop().getStopCode());
			csvOutput.write(stopActivity.getDate());
			csvOutput.write(stopActivity.getDayOfWeek());
			csvOutput.write(stopActivity.getArrivalTime());
			csvOutput.write(stopActivity.getDepartureTime());
			csvOutput.write(stopActivity.getForecastDepartureTime());
			csvOutput.write(stopActivity.getLastDepartureTime());
			csvOutput.write(vehicle.getVehicleName() + "" + vehicle.getCurrentTripNumber());
			csvOutput.write("" + vehicle.getCurrentPassengerOn());
			csvOutput.write("" + stopActivity.getNumberPassengerOn());
			csvOutput.write("" + stopActivity.getNumberPassengerOff());
			csvOutput.write("" + stopActivity.getNumberPassengerLeftOver());
			csvOutput.endRecord();
			
			
			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
