package uow.cmde.transim.transit.controller;


import java.util.*;
import uow.cmde.transim.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 28/03/2012
 */
public class TransitParameters {

	private int numberPassengerWait = 0;
	private int numberPassengerOnVehicle = 0;
	private int numberPassengerAlighting = 0;
	private int numberPassengerBoarding = 0;
	private int runningTime = 0;
	private int dwellTime = 0;
	private int standingCapacity;
	private int seatedCapacity;
	private int totalCapacity;
	
	private int forecastNumberPassengerOnVehicle = 0;
	private int forecastNumberPassengerAlighting = 0;
	private int forecastNumberPassengerBoarding = 0;
	private int forecastNumberPassengerLeftOver = 0;
	private int forecastHeadway = 0;
	
	
	public static Map<String,TransitParameters> listTransitParameters = new HashMap<String,TransitParameters>();
	
	
	public TransitParameters(){}
	
	
	
	
	public double getExpectedWaitTime()
	{
		double expectedWaitTime = AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE/2 + AppConfig.TRANSIT_STANDARD_HEADWAY_DEVIATION_IN_MINUTE/(2* AppConfig.TRANSIT_SCHEDULE_HEADWAY_IN_MINUTE);
		
		return expectedWaitTime;
	}
	
	
	public void setNumberPassengerWait(int numberPassengerWait)
	{
		this.numberPassengerWait = numberPassengerWait;
	}
	
	public int getNumberPassengerWait()
	{
		return this.numberPassengerWait;
	}
	
	
	public void setNumberPassengerAlighting(int numberPassengerAlighting)
	{
		this.numberPassengerAlighting = numberPassengerAlighting;
	}
	
	public int getNumberPassengerAlighting()
	{
		return this.numberPassengerAlighting;
	}
	
	public void setNumberPassengerBoarding(int numberPassengerBoarding)
	{
		this.numberPassengerBoarding = numberPassengerBoarding;
	}
	
	public int getNumberPassengerBoarding()
	{
		return this.numberPassengerBoarding;
	}

	public int getNumberPassengerOnVehicle()
	{
		return this.numberPassengerOnVehicle;
	}
	
	public void setNumberPassengerOnVehicle(int numberPassengerOnVehicle)
	{
		this.numberPassengerOnVehicle = numberPassengerOnVehicle;
	}
	
	public void setRunningTime(int runningTime)
	{
		this.runningTime = runningTime;
	}
	
	public int getRunningTime()
	{
		return this.runningTime;
	}
	
	public void setDwellTime(int dwellTime)
	{
		this.dwellTime = dwellTime;
	}
	
	public int getDwellTime()
	{
		return this.dwellTime;
	}
	
	public int getStandingCapacity()
	{
		return this.standingCapacity;
	}
	
	public void setStandingCapacity(int standingCapacity)
	{
		this.standingCapacity = standingCapacity;
		
		//System.out.println("" + standingCapacity);
	}
	
	public int getSeatedCapacity()
	{
		return this.seatedCapacity;
	}
	
	
	public void setSeatedCapacity(int seatedCapacity)
	{
		this.seatedCapacity = seatedCapacity;
		
		//System.out.println("" + seatedCapacity);
	}
	
	public void setTotalVehicleCapacity(int totalCapacity)
	{
		this.totalCapacity = totalCapacity;
	}
	
	public int getTotalVehicleCapacity()
	{
		return this.totalCapacity;
	}
	
	public void setForecastNumberPassengerLeftOver(int numberPassengerLeftOver)
	{
		this.forecastNumberPassengerLeftOver = numberPassengerLeftOver;
	}
	
	public int getForecastNumberPassengerLeftOver()
	{
		return this.forecastNumberPassengerLeftOver;
	}
	
	public void setForecastNumberPassengerOnVehicle(int numberPassengerOnVehicle)
	{
		this.forecastNumberPassengerOnVehicle = numberPassengerOnVehicle;
	}
	
	public int getForecastNumberPassengerOnVehicle()
	{
		return this.forecastNumberPassengerOnVehicle;
	}
	
	public void setForecastNumberPassengerAlighting(int numberPassengerAlighting)
	{
		this.forecastNumberPassengerAlighting = numberPassengerAlighting;
	}
	
	public int getForecastNumberPassengerAlighting()
	{
		return this.forecastNumberPassengerAlighting;
	}
	
	public void setForecastNumberPassengerBoarding(int forecastNumberPassengerBoarding)
	{
		this.forecastNumberPassengerBoarding = forecastNumberPassengerBoarding;
	}
	
	public void setForecastHeadwayInMinute(int headway)
	{
		this.forecastHeadway = headway;
	}
	
	public int getForecastHeadwayInMinute()
	{
		return this.forecastHeadway;
	}
	
	public int getForecastNumberPassengerBoarding()
	{
		return this.forecastNumberPassengerBoarding;
	}
	
	public static void addOrUpdateTransitParameters(String vehicleName, TransitParameters transitParameters)
	{
		
		if(TransitParameters.listTransitParameters.containsKey(vehicleName))
		{
			TransitParameters.listTransitParameters.remove(vehicleName);
		}
		TransitParameters.listTransitParameters.put(vehicleName,  transitParameters);
		
		
	}
	

	
	
}
