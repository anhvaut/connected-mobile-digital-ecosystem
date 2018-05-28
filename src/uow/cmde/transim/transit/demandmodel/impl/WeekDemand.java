package uow.cmde.transim.transit.demandmodel.impl;

import java.util.*;

import uow.cmde.transim.util.constants.*;
import uow.cmde.transim.transit.demandmodel.*;

/**
 * 
 * @author Vu The Tran
 * @since 01/01/2012
 */
public class WeekDemand implements IWeekDemand{

	private Map<String,DateDemand> weekDemand;
	
	public WeekDemand()
	{
		weekDemand = new HashMap<String,DateDemand>();
		
		weekDemand.put(WeekDay.MONDAY, new DateDemand());
		weekDemand.put(WeekDay.TUESDAY, new DateDemand());
		weekDemand.put(WeekDay.WEDNESDAY, new DateDemand());
		weekDemand.put(WeekDay.THURSDAY, new DateDemand());
		weekDemand.put(WeekDay.FRIDAY, new DateDemand());
	
	}
	
	public DateDemand getDateDemand(String dayOfWeek)
	{
		DateDemand dateDemand = null;
		
		if(weekDemand.containsKey(dayOfWeek))
			dateDemand = weekDemand.get(dayOfWeek);
		
		return dateDemand;
	}
	
	public void addValuePassengerOnPerDay(String dayOfWeek, int numberPassengerOn)
	{
		DateDemand dateDemand  = getDateDemand(dayOfWeek);
		if(dateDemand!=null)
		{
			dateDemand.addValuePassengerOnPerDay(numberPassengerOn);
		}
	}
	
	public void addValuePassengerOffPerDay(String dayOfWeek, int numberPassengerOff)
	{
		DateDemand dateDemand  = getDateDemand(dayOfWeek);
		if(dateDemand!=null)
		{
			dateDemand.addValuePassengerOffPerDay(numberPassengerOff);
		}
	}
	
	public void addValuePassengerOnPerTimeAndDay(String dayOfWeek,String timeKey, int numberPassengerOn)
	{
		DateDemand dateDemand  = getDateDemand(dayOfWeek);
		if(dateDemand!=null)
		{
			dateDemand.addValuePassengerOnPerTimeAndDay(timeKey, numberPassengerOn);
		}
		
	}
	
	public void addValuePassengerOffPerTimeAndDay(String dayOfWeek,String timeKey, int numberPassengerOff)
	{
		DateDemand dateDemand  = getDateDemand(dayOfWeek);
		if(dateDemand!=null)
		{
			dateDemand.addValuePassengerOffPerTimeAndDay(timeKey, numberPassengerOff);
		}
		
	}
	
}
