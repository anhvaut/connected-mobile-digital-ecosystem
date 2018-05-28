package uow.cmde.transim.transit.demandmodel.impl;

import uow.cmde.transim.transit.demandmodel.*;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import java.util.*;

/**
 * 
 * Passenger demand for a date
 * @author Vu The Tran
 * @since 01/01/2012
 */
public class DateDemand implements IDateDemand {
	
	//Mean per minute based on time period
	private Hashtable statsPassengerOnPerTimeAndDay;
	private Hashtable statsPassengerOffPerTimeAndDay;
	private DescriptiveStatistics statsPassengerOnPerDay;
	private DescriptiveStatistics statsPassengerOffPerDay;
	
	public DateDemand(){
		
		statsPassengerOnPerDay = new DescriptiveStatistics();
		statsPassengerOffPerDay = new DescriptiveStatistics();
		statsPassengerOnPerTimeAndDay = new Hashtable();
		statsPassengerOffPerTimeAndDay = new Hashtable();
	}
	
	/**
	 * addValuePassengerOn
	 * @param numberPassengerOn
	 */
	public void addValuePassengerOnPerDay(int numberPassengerOn)
	{
		statsPassengerOnPerDay.addValue((double)numberPassengerOn);
	}
	
	/**
	 * addValuePassengerOff
	 * @param numberPassengerOff
	 */
	public void addValuePassengerOffPerDay(int numberPassengerOff)
	{
		statsPassengerOffPerDay.addValue((double)numberPassengerOff);
	}
	
	/**
	 * addValuePassengerOnPerMinute
	 * @param timeKey
	 * @param numberPassengerOn
	 */
	public void addValuePassengerOnPerTimeAndDay(String timeKey, int numberPassengerOn)
	{
		
		if(statsPassengerOnPerTimeAndDay.containsKey(timeKey))
		{
			DescriptiveStatistics stat = (DescriptiveStatistics) statsPassengerOnPerTimeAndDay.get(timeKey);
			stat.addValue((double)numberPassengerOn);
		}
		else
		{
			DescriptiveStatistics stat = new DescriptiveStatistics();
			stat.addValue(numberPassengerOn);
			statsPassengerOnPerTimeAndDay.put(timeKey, stat);
		}
		
	}
	
	/**
	 * addValuePassengerOffPerMinute
	 * @param timeKey
	 * @param numberPassengerOff
	 */
	public void addValuePassengerOffPerTimeAndDay(String timeKey, int numberPassengerOff)
	{
		if(statsPassengerOffPerTimeAndDay.containsKey(timeKey))
		{
			DescriptiveStatistics stat = (DescriptiveStatistics) statsPassengerOffPerTimeAndDay.get(timeKey);
			stat.addValue((double)numberPassengerOff);
		}
		else
		{
			DescriptiveStatistics stat = new DescriptiveStatistics();
			stat.addValue(numberPassengerOff);
			statsPassengerOffPerTimeAndDay.put(timeKey, stat);
		}
	}
	
	/**
	 * getMeanPassengerOn
	 * @return
	 */
	public double getMeanPassengerOnPerDay()
	{
		return statsPassengerOnPerDay.getMean();
	}
	
	
	/**
	 * getMeanPassengerOff
	 * @return
	 */
	public double getMeanPassengerOffPerDay()
	{
		return statsPassengerOffPerDay.getMean();
	}
	
	public double getMeanPassengerOnPerTimeAndDay(String timeKey)
	{
		if(statsPassengerOnPerTimeAndDay.containsKey(timeKey))
		{
			DescriptiveStatistics stat = (DescriptiveStatistics) statsPassengerOnPerTimeAndDay.get(timeKey);
			return stat.getMean();
		}
		
		return -1;
	}
	
	/**
	 * 
	 * getMeanPassengerOffPerMinute
	 * @param timeKey
	 * @return
	 */
	public double getMeanPassengerOffPerTimeAndDay(String timeKey)
	{
		
		if(statsPassengerOffPerTimeAndDay.containsKey(timeKey))
		{
			DescriptiveStatistics stat = (DescriptiveStatistics) statsPassengerOffPerTimeAndDay.get(timeKey);
			return stat.getMean(); 
		}
		
		return -1;
	}
	
	/**
	 * setMeanPassengerOnPerMinute
	 * @param meanPassengerOn15Minute
	 */
	public void setMeanPassengerOnPerTimeAndDay(Hashtable meanPassengerOnPerTimeAndDay)
	{
		this.statsPassengerOnPerTimeAndDay = meanPassengerOnPerTimeAndDay;
	}
	
	/**
	 * setMeanPassengerOff15Minute
	 * @param meanPassengerOff15Minute
	 */
	public void setMeanPassengerOffPerTimeAndDay(Hashtable meanPassengerOffPerTimeAndDay)
	{
		this.statsPassengerOffPerTimeAndDay = meanPassengerOffPerTimeAndDay;
	}
	
	
	public double getTotalPassengerOnPerDay()
	{
		return statsPassengerOnPerDay.getSum();
	}
	
	public double getTotalPassengerOffPerDay()
	{
		return statsPassengerOffPerDay.getSum();
	}

}
