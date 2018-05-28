package uow.cmde.transim.transit.controller;

import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 28/03/2012
 */
public class TransitFormular {

	
	public static void calculate(TransitParameters transitParameter)
	{
		int numberPassengerOnVehicle =0, dwellTime =0, numberPassengerAlighting =0, numberPassengerBoarding =0, numberPassengerLeftOver = 0;
		
		numberPassengerOnVehicle = transitParameter.getNumberPassengerOnVehicle();
		numberPassengerAlighting = (int)(numberPassengerOnVehicle * AppConfig.TRANSIT_PASSENGER_ALIGHTING_FRACTION);
		numberPassengerOnVehicle -= numberPassengerAlighting;
		
		int leftVehicleCapacity = transitParameter.getTotalVehicleCapacity() - numberPassengerOnVehicle;
		
		if(leftVehicleCapacity < transitParameter.getNumberPassengerWait())
		{
			numberPassengerBoarding = leftVehicleCapacity;
		}
		else
		{
			numberPassengerBoarding =  transitParameter.getNumberPassengerWait();
		}
		
		dwellTime = AppConfig.TRANSIT_DECELERATION_TIME;
	    dwellTime += numberPassengerBoarding * AppConfig.TRANSIT_AVERAGE_BOARDING_TIME_EACH_PASSENGER;
	    dwellTime += numberPassengerAlighting * AppConfig.TRANSIT_AVERAGE_ALIGHTING_TIME_EACH_PASSENGER;
	    
	    numberPassengerOnVehicle += numberPassengerBoarding;
	    numberPassengerLeftOver =  transitParameter.getNumberPassengerWait() - numberPassengerBoarding;
	    
	    transitParameter.setDwellTime(dwellTime);
	    transitParameter.setForecastNumberPassengerOnVehicle(numberPassengerOnVehicle);
	    transitParameter.setForecastNumberPassengerAlighting(numberPassengerAlighting);
	    transitParameter.setForecastNumberPassengerBoarding(numberPassengerBoarding);
	    transitParameter.setForecastNumberPassengerLeftOver(numberPassengerLeftOver);
	   
	}
	
	
	public static String calculateDepartureTime(int dwellTime, String arrivalTime)
	{	
		int seconds = dwellTime + TimeConverter.convertTimeToSecond(arrivalTime);
		
		return TimeConverter.convertSecondToTime(seconds);
	}
	
	public static String calculateForecastArrivalTime(double distanceToStopInKm,String currentTime)
	{
		double travelTime = (distanceToStopInKm / AppConfig.TRANSIT_AVERAGE_SPEED_KM_PER_HOUR) * 3600;
		
		//System.out.println(distanceToStopInKm  + " travelTime:" + travelTime);
		String arrivalTime = TimeConverter.convertSecondToTime(TimeConverter.convertTimeToSecond(currentTime) + (int)travelTime);
		
		//System.out.println(currentTime + " -- " + arrivalTime);
		
		return arrivalTime;
		
	}
	
	public static int calculateHeadwayInMinute(String lastDepartureTime, String forecastDepartureTime)
	{
		int headwayInSecond = 0;
		if(lastDepartureTime!=null && !lastDepartureTime.equals(""))
		{
			headwayInSecond = TimeConverter.convertTimeToSecond(forecastDepartureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
			
		}
		
		return (headwayInSecond/60);
	}
}
