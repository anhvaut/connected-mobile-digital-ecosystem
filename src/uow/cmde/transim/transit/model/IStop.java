package uow.cmde.transim.transit.model;

import uow.cmde.transim.transit.demandmodel.impl.PassengerDemand;

import java.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 12/11/2011
 */
public interface IStop {
	
	
	public void setStopId(int stopId);
	
	public int getStopId();
	
	
	public void setStopCode(String stopCode);
	
	public String getStopCode();
	
	
	public void setStopName(String stopName);
	
	public String getStopName();
	
	
	public void setStopDesc(String stopDesc);
	
	public String getStopDesc();
	
	
	public void setStopLat(double stopLat);
	
	public double getStopLat();
	
	
	public void setStopLon(double stopLon);
	
	public double getStopLon();
	
	
	public int getZoneId();
	
	public void setZoneId(int zoneId);
	
	
	public String getStopUrl();
	
	public void setStopUrl(String stopUrl);
	
	
	public int getLocationType();
	
	public void setLocationType(int locationType);
	
	
	public int getParentStation();
	
	public void setParentStation(int parentStation);
	
	
	public void setPassengerDemand(PassengerDemand passengerDemand);
	
	public PassengerDemand getPassengerDemand();
	
	
	public void addPassengerWaiting(int passenger);
	
	public int getPassengerWaiting();
	
	public void setPassengerWaiting(int passengerWaiting);
	
	
	public void setPassengerOffRate(int passengerAlightingRate);
	
	public int getPassengerOffRate();
	
	
	public String getLastDepartureTime();
	
	public void setLastDepartureTime(String lastDepartureTime);
	
	public int getNumberOfWaitingVehicle();
	
	public void removeWaitingVehicle(int vehicleIndex);
	
	public void addWaitingVehicle(int vehicleIndex);
	
	public int getFirstWaitingVehicle();
	
	public boolean isFirstWaitingVehicle(int vehicleIndex);
	
	public void removeFirstWaitingVehicle();
	
	public boolean hasVehicleWaiting();
	
	public void printWaitingVehicle();
	
	public void reset();
	
}
