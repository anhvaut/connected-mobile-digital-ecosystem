package uow.cmde.transim.historydata.report;


import uow.cmde.transim.historydata.StopActivityQueryer;
import uow.cmde.transim.transit.model.impl.*;
import uow.cmde.transim.transit.model.*;

/**
 * 
 * @author Vu The Tran
 * @since 21/12/2011
 */
public class HistoryDataQueryer {

	/**
	 * getPassengerByTimeAndDate
	 * @return
	 */
	public static IStopActivities getPassengerByTimeAndDate() throws Exception
	{
		
		
		IStopActivities stopActivities = new StopActivities();
	
		StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
		String sql = "select sum(number_passenger_on),sum(number_passenger_off),date,time from manual_stop_activity group by date,time order by time,date";
			
		stopActivityHandler.setIndexNumberPassengerOn(1);
		stopActivityHandler.setIndexNumberPassengerOff(2);
		stopActivityHandler.setIndexDate(3);
		stopActivityHandler.setIndexArrivalTime(4);
			
		stopActivities = stopActivityHandler.queryStopActivity(sql);
	        
		
		return stopActivities;
	}
	
	/**
	 * getPassengerOnOffAtEachBusStop
	 * @return
	 */
	public static IStopActivities getPassengerOnOffAtEachBusStop() throws Exception
	{
		IStopActivities stopActivities = new StopActivities();
	
		StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
		String sql = "select sum(number_passenger_on),sum(number_passenger_off), manual_bus_stop.stop_name";
		sql +=" from manual_stop_activity, manual_bus_stop";
		sql +=" where manual_stop_activity.stop_code = manual_bus_stop.stop_code ";
		sql +=" group by manual_bus_stop.stop_name";
		sql +=" order by manual_bus_stop.stop_code ";
			
		stopActivityHandler.setIndexNumberPassengerOn(1);
		stopActivityHandler.setIndexNumberPassengerOff(2);
		stopActivityHandler.setIndexStopName(3);
			
		stopActivities = stopActivityHandler.queryStopActivity(sql);
	        
		return stopActivities;
	}		
	
	/**
	 * getPassengerPerDay
	 * @return
	 */
	public static IStopActivities getPassengerPerDay() throws Exception
	{
		IStopActivities stopActivities = new StopActivities();
		
		StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
		String sql = "select sum(number_passenger_on),sum(number_passenger_off), date";
		sql +=" from manual_stop_activity";
		sql +=" group by date";
			
		stopActivityHandler.setIndexNumberPassengerOn(1);
		stopActivityHandler.setIndexNumberPassengerOff(2);
		stopActivityHandler.setIndexDate(3);
			
		stopActivities = stopActivityHandler.queryStopActivity(sql);
	        
		
		return stopActivities;
	}
	
	
	public static void main(String[] args) {
		//JFreeChart chart =ChartHandler.CreatePassengerByTimeAndDate(HistoryDataHandler.getPassengerByTimeAndDate());
		//JFreeChart chart =ChartHandler.CreatePassengerOnOffAtEachBusStop(HistoryDataHandler.getPassengerOnOffAtEachBusStop());
		//JFreeChart chart =ChartHandler.CreatePassengerPerDay(HistoryDataHandler.getPassengerPerDay());
		//ChartHandler.saveChart(chart, "D:/test4.jpg");
	}
}
