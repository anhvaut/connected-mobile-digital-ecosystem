package uow.cmde.transim.transit.demandmodel.impl;

import uow.cmde.transim.util.constants.WeekDay;
import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.historydata.report.HistoryDataQueryer;
import uow.cmde.transim.historydata.*;

/**
 * 
 * @author Vu The Tran
 * @since 01/01/2012
 */
public class HistoryPassengerDemand extends PassengerDemand {

	private String stopCode;
	private IHistoryDemand historyDemand;
	
	/**
	 * HistoryPassengerDemand
	 * @param stopCode
	 */
	public HistoryPassengerDemand(String stopCode) throws Exception
	{
		this.stopCode = stopCode;
		this.historyDemand = new SqlHistoryDemand();
		
		calculateDemandByDay();
		calculateDemandByTimeAndDay();
		
	}
	
	/**
	 * calculateDemandByTimeAndDay
	 */
	private void calculateDemandByTimeAndDay() throws Exception
	{
		IStopActivities stopActivities = historyDemand.getDemandByTimeAndDay(this.stopCode);
		
		for(IStopActivity stopActivity:stopActivities.getAllStopActivities())
		{
		
			String dayOfWeek = getDayOfWeek(stopActivity.getDate());
			
			String time = stopActivity.getArrivalTime() + ":00";
		
			weekDemand.addValuePassengerOnPerTimeAndDay(dayOfWeek,time, stopActivity.getNumberPassengerOn());
			weekDemand.addValuePassengerOffPerTimeAndDay(dayOfWeek,time, stopActivity.getNumberPassengerOff());
			
			//System.out.println(time);
		}
	}
	
	/**
	 * calculateDemandByDay
	 */
	private void calculateDemandByDay() throws Exception
	{
		
		IStopActivities stopActivities = historyDemand.getDemandByDay(this.stopCode);
		
		for(IStopActivity stopActivity:stopActivities.getAllStopActivities())
		{
			String dayOfWeek = getDayOfWeek(stopActivity.getDate());
			weekDemand.addValuePassengerOnPerDay(dayOfWeek,stopActivity.getNumberPassengerOn());
			weekDemand.addValuePassengerOffPerDay(dayOfWeek,stopActivity.getNumberPassengerOff());
			
		}
	}
	
	
	
	/**
	 * getDayOfWeek
	 * @param date
	 * @return
	 */
	private String getDayOfWeek(String date)
	{
		String dayOfWeek = "";
		if(date.indexOf(WeekDay.MONDAY)!=-1)
		{
			dayOfWeek = WeekDay.MONDAY;
		}
		else if(date.indexOf(WeekDay.TUESDAY)!=-1)
		{
			dayOfWeek = WeekDay.TUESDAY;
		}
		else if(date.indexOf(WeekDay.WEDNESDAY)!=-1)
		{
			dayOfWeek = WeekDay.WEDNESDAY;
		}
		else if(date.indexOf(WeekDay.THURSDAY)!=-1)
		{
			dayOfWeek = WeekDay.THURSDAY;
			
		}
		else if(date.indexOf(WeekDay.FRIDAY)!=-1)
		{
			dayOfWeek = WeekDay.FRIDAY;
		}
		
		return dayOfWeek;
	}
	
	/**
	 * initalizePassengerDemand
	 * @param routes
	 */
	public static void initalizePassengerDemand(IRoutes routes) throws Exception
	{
		PassengerDemand passengerDemand;
		for(IRoute route:routes.getAllRoutes())
		{
			for(IStop stop:route.getSequenceStops().getAllStops())
			{
				
				String stopCode = stop.getStopCode();
				passengerDemand = new HistoryPassengerDemand(stopCode); 
				stop.setPassengerDemand(passengerDemand);
			}
		}
	}
	
	
	public static void updatePassengerDemandPerTimeAndDay(IRoutes routes, String dayOfWeek, String time)
	{
		for(IRoute route:routes.getAllRoutes())
		{
			for(IStop stop:route.getSequenceStops().getAllStops())
			{
				//double passengerWaiting = stop.getPassengerDemand().generatePassengerOnAtStopPerTimeAndDay(dayOfWeek, time);
				double passengerArrivalRate = stop.getPassengerDemand().generatePassengerArrivalRatePerMinute(dayOfWeek, time);
				double passengerOffRate = stop.getPassengerDemand().generatePassengerOffRatePerTimeAndDay(dayOfWeek, time);
				
				if(passengerArrivalRate!=-1)
				{
					stop.addPassengerWaiting((int)passengerArrivalRate);
				}
				
				if(passengerOffRate!=-1)
				{
					stop.setPassengerOffRate((int)passengerOffRate);
				}
				
				
			}
		}
	}
	
	
}
