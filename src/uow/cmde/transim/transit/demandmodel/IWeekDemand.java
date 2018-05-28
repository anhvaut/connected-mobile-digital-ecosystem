package uow.cmde.transim.transit.demandmodel;

import java.util.*;

import uow.cmde.transim.util.constants.*;

/**
 * 
 * @author Vu The Tran
 * @since 01/01/2012
 */
public interface IWeekDemand {


	
	public IDateDemand getDateDemand(String dayOfWeek);
	
	public void addValuePassengerOnPerDay(String dayOfWeek, int numberPassengerOn);
	public void addValuePassengerOffPerDay(String dayOfWeek, int numberPassengerOff);
	public void addValuePassengerOnPerTimeAndDay(String dayOfWeek,String timeKey, int numberPassengerOn);
	public void addValuePassengerOffPerTimeAndDay(String dayOfWeek,String timeKey, int numberPassengerOff);
	
}
