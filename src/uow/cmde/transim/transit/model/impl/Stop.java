package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.demandmodel.impl.PassengerDemand;
import uow.cmde.transim.transit.model.*;
import java.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public class Stop implements IStop{
	
	/**
	 * contains an ID that uniquely identifies a stop or station
	 */
	private int stopId;
	
	/**
	 * contains short text or a number that uniquely identifies the stop for passengers
	 */
	private String stopCode;
	
	/**
	 * contains the name of a stop or station
	 */
	private String stopName;
	
	/**
	 * contains a description of a stop
	 */
	private String stopDesc;
	
	/**
	 * contains the latitude of a stop or station
	 */
	private double stopLat;
	
	/**
	 * contains the longitude of a stop or station
	 */
	private double stopLon;
	
	/**
	 * defines the fare zone for a stop ID
	 */
	private int zoneId;
	
	/**
	 * contains the URL of a web page about a particular stop
	 */
	private String stopUrl;
	
	/**
	 * identifies whether this stop ID represents a stop or station
	 */
	private int locationType;
	
	/**
	 * identifies the station associated with the stop
	 */
	private int parentStation;
	
	private PassengerDemand passengerDemand;
	
	private int passengerWaiting = 0;
	
	private int passengerAlightingRate =0;
	
	private String lastDepartureTime = "";
	
	private ArrayList waitingVehicle = new ArrayList();
	
	
	public void setStopId(int stopId)
	{
		this.stopId = stopId;
	}
	
	public int getStopId()
	{
		return this.stopId;
	}
	
	public void setStopCode(String stopCode)
	{
		this.stopCode = stopCode;
	}
	
	public String getStopCode()
	{
		return this.stopCode;
	}
	
	public void setStopName(String stopName)
	{
		this.stopName = stopName;
	}
	
	public String getStopName()
	{
		return this.stopName;
	}
	
	public void setStopDesc(String stopDesc)
	{
		this.stopDesc = stopDesc;
	}
	
	public String getStopDesc()
	{
		return stopDesc;
	}
	
	public void setStopLat(double stopLat)
	{
		this.stopLat = stopLat;
	}
	
	public double getStopLat()
	{
		return this.stopLat;
	}
	
	public void setStopLon(double stopLon)
	{
		this.stopLon = stopLon;
	}
	
	public double getStopLon()
	{
		return this.stopLon;
	}
	
	public int getZoneId()
	{
		return this.zoneId;
	}
	
	public void setZoneId(int zoneId)
	{
		this.zoneId = zoneId;
	}
	
	public String getStopUrl()
	{
		return this.stopUrl;
	}
	
	public void setStopUrl(String stopUrl)
	{
		this.stopUrl = stopUrl;
	}
	
	public int getLocationType()
	{
		return this.locationType;
	}
	
	public void setLocationType(int locationType)
	{
		this.locationType = locationType;
	}
	
	public int getParentStation()
	{
		return this.parentStation;
	}
	
	public void setParentStation(int parentStation)
	{
		this.parentStation = parentStation;
	}
	
	public void setPassengerDemand(PassengerDemand passengerDemand)
	{
		this.passengerDemand = passengerDemand;
	}
	
	public PassengerDemand getPassengerDemand()
	{
		return passengerDemand;
	}
	
	public void addPassengerWaiting(int passenger)
	{
		this.passengerWaiting += passenger;
	}
	
	public int getPassengerWaiting()
	{
		return this.passengerWaiting;
	}
	
	public void setPassengerWaiting(int passengerWaiting)
	{
		this.passengerWaiting = passengerWaiting;
	}
	
	public void setPassengerOffRate(int passengerAlightingRate)
	{
		this.passengerAlightingRate = passengerAlightingRate;
	}
	
	public int getPassengerOffRate()
	{
		return this.passengerAlightingRate;
	}
	
	public String getLastDepartureTime()
	{
		return this.lastDepartureTime;
	}
	
	public void setLastDepartureTime(String lastDepartureTime)
	{
		this.lastDepartureTime = lastDepartureTime;
	}
	
	public int getNumberOfWaitingVehicle()
	{
		return waitingVehicle.size();
	}
	
	public void addWaitingVehicle(int vehicleIndex)
	{
		if(!waitingVehicle.contains("" + vehicleIndex))
		{
			waitingVehicle.add("" + vehicleIndex);
		}
	}
	
	public void removeWaitingVehicle(int vehicleIndex)
	{
		if(waitingVehicle.contains("" + vehicleIndex))
		{
			waitingVehicle.remove("" + vehicleIndex);
			//System.out.println("remove:" + vehicleIndex);
		}
		
	}
	
	
	public int getFirstWaitingVehicle()
	{
		if(hasVehicleWaiting())
		{
			return Integer.parseInt(waitingVehicle.get(0).toString());
		}
		
		return -1;
	}
	
	public boolean isFirstWaitingVehicle(int vehicleIndex)
	{
		if(hasVehicleWaiting())
		{
			return waitingVehicle.get(0).toString().equals("" + vehicleIndex);
		}
		
		return false;
	}
	
	public void removeFirstWaitingVehicle()
	{
		if((waitingVehicle.size() > 0))
		{
			waitingVehicle.remove(0);
		}
	}
	
	public boolean hasVehicleWaiting()
	{
		return (waitingVehicle.size() > 0);
	}
	
	public void printWaitingVehicle()
	{
		for(int i=0;i<waitingVehicle.size();i++)
		{
			System.out.println("w:" + waitingVehicle.get(i));
		}
	}
	
	public void reset()
	{
		passengerWaiting = 0;
		lastDepartureTime = "";
		waitingVehicle.clear();
	}
}
