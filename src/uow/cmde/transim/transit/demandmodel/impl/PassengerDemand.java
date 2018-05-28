package uow.cmde.transim.transit.demandmodel.impl;

import java.util.Random;
import org.uncommons.maths.random.PoissonGenerator;

import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.transit.controller.TransitParameters;
import uow.cmde.transim.transit.demandmodel.*;
import uow.cmde.transim.util.*;


/**
 * 
 * @author Vu The Tran
 * @since 01/01/2012
 */
public class PassengerDemand implements IPassengerDemand{

	protected IWeekDemand weekDemand;
	private TransitParameters transitParameters;
	
	public PassengerDemand()
	{
		weekDemand = new WeekDemand();
	}
	
	/**
	 * generatePassengerOffAtStop
	 * @param dayOfWeek
	 * @param time
	 * @return
	 */
	public double generatePassengerOffAtStop(String dayOfWeek, String time)
	{
	
		Random rng = new Random();
		double mean = weekDemand.getDateDemand(dayOfWeek).getMeanPassengerOffPerTimeAndDay(time);
		if(mean ==0) mean = 0.5; 
				
		PoissonGenerator p=new PoissonGenerator(mean, rng);
		
		return p.nextValue();
	}
	
	/**
	 * generatePassengerOnAtStop
	 * @param dayOfWeek
	 * @param time
	 * @return
	 */
	public double generatePassengerOnAtStopPerTimeAndDay(String dayOfWeek, String time)
	{
		double mean = weekDemand.getDateDemand(dayOfWeek).getMeanPassengerOnPerTimeAndDay(time);
		if(mean!=-1)
		{
			Random rng = new Random();
			
			if(mean ==0) mean = 0.5; 
			PoissonGenerator p=new PoissonGenerator(mean, rng);
			return p.nextValue();
		}
		
		return -1;
	}
	
	
	/**
	 * simulatePassengerArrivaleAtStop
	 * @param dayOfWeek
	 * @param time
	 * @return
	 */
	public double generatePassengerArrivalRatePerMinute(String dayOfWeek, String time)
	{
		
		if(TimeConverter.isStartNewMinute(time))
		{
			double mean15Minutes = weekDemand.getDateDemand(dayOfWeek).getMeanPassengerOnPerTimeAndDay(TimeConverter.convertTimePoint(time));
			
			if(mean15Minutes!=-1)
			{
				double mean = mean15Minutes /15;
				
				if(mean!=-1)
				{
					Random rng = new Random();
					
					if(mean ==0) mean = 0.5; 
					PoissonGenerator p=new PoissonGenerator(mean, rng);
					return p.nextValue()* AppConfig.TRANSIT_DEMAND_SCALE;
				}
			}
		}
		
		return -1;
	}
	
	public double generatePassengerOffRatePerTimeAndDay(String dayOfWeek, String time)
	{
		double mean = weekDemand.getDateDemand(dayOfWeek).getMeanPassengerOffPerTimeAndDay(time);
		
		if(mean!=-1)
		{
			Random rng = new Random();
			if(mean ==0) mean = 0.5; 
			
			PoissonGenerator p=new PoissonGenerator(mean, rng);
			
			return p.nextValue();
		}
		
		return -1;
	}
	public IWeekDemand getWeekDemand()
	{
		return weekDemand;
	}
	
	public void setWeekDemand(IWeekDemand weekDemand)
	{
		this.weekDemand = weekDemand;
	}
	
	
}
