package uow.cmde.transim.transit.demandmodel;


import java.util.*;

/**
 * 
 * Passenger demand for a date
 * @author Vu The Tran
 * @since 01/01/2012
 */
public interface IDateDemand {
	

	public void addValuePassengerOnPerDay(int numberPassengerOn);
	public void addValuePassengerOffPerDay(int numberPassengerOff);
	public void addValuePassengerOnPerTimeAndDay(String timeKey, int numberPassengerOn);
	public void addValuePassengerOffPerTimeAndDay(String timeKey, int numberPassengerOff);
	public double getMeanPassengerOnPerDay();
	public double getMeanPassengerOffPerDay();
	
	public double getMeanPassengerOnPerTimeAndDay(String timeKey);
	public double getMeanPassengerOffPerTimeAndDay(String timeKey);
	public void setMeanPassengerOnPerTimeAndDay(Hashtable meanPassengerOnPerTimeAndDay);

	public void setMeanPassengerOffPerTimeAndDay(Hashtable meanPassengerOffPerTimeAndDay);
	public double getTotalPassengerOnPerDay();
	
	public double getTotalPassengerOffPerDay();

}
