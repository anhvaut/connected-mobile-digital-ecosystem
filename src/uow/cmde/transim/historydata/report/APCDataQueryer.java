package uow.cmde.transim.historydata.report;


import uow.cmde.transim.historydata.StopActivityQueryer;
import uow.cmde.transim.transit.model.impl.*;

/**
 * 
 * @author Vu The Tran
 * @since 21/12/2011
 */
public class APCDataQueryer {

	private static String dbName = "avl_apc_db";
	private static String dbUser = "root";
	private static String dbPass = "";
	
	/**
	 * getPassengerByTimeAndDate
	 * @return
	 */
	public static StopActivities getPassengerByTimeAndDate()
	{
		
		
		StopActivities stopActivities = new StopActivities();
		try
		{
			StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
			stopActivityHandler.setDatabaseInfo(dbName, dbUser, dbPass);
			
			String sql = "select sum(number_passenger_on),sum(number_passenger_off),date,time from apcdataprocessed group by date,time order by time,date";
			
			stopActivityHandler.setIndexNumberPassengerOn(1);
			stopActivityHandler.setIndexNumberPassengerOff(2);
			stopActivityHandler.setIndexDate(3);
			stopActivityHandler.setIndexArrivalTime(4);
			
			stopActivities = stopActivityHandler.queryStopActivity(sql);
	        
		}
		catch(Exception ex)
		{
			System.out.println("getPassengerByTimeAndDate:" + ex.getMessage());
		}
		
		return stopActivities;
	}
	
	/**
	 * getPassengerOnOffAtEachBusStop
	 * @return
	 */
	public static StopActivities getPassengerOnOffAtEachBusStop()
	{
		StopActivities stopActivities = new StopActivities();
		try
		{
			StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
			stopActivityHandler.setDatabaseInfo(dbName, dbUser, dbPass);
			
			String sql = "select sum(number_passenger_on),sum(number_passenger_off), stop_name ";
			sql +=" from apcdataprocessed ";
			//sql +=" where manual_stop_activity.stop_code = manual_bus_stop.stop_code ";
			sql +=" group by stop_name";
			
			
			stopActivityHandler.setIndexNumberPassengerOn(1);
			stopActivityHandler.setIndexNumberPassengerOff(2);
			stopActivityHandler.setIndexStopName(3);
			
			stopActivities = stopActivityHandler.queryStopActivity(sql);
	        
		}
		catch(Exception ex)
		{
			System.out.println("getPassengerOnOffAtEachBusStop" + ex.getMessage());
		}
		
		return stopActivities;
	}		
	
	/**
	 * getPassengerPerDay
	 * @return
	 */
	public static StopActivities getPassengerPerDay()
	{
		StopActivities stopActivities = new StopActivities();
		try
		{
			StopActivityQueryer stopActivityHandler = new StopActivityQueryer();
			stopActivityHandler.setDatabaseInfo(dbName, dbUser, dbPass);
			
			String sql = "select sum(number_passenger_on),sum(number_passenger_off), date";
			sql +=" from apcdataprocessed";
			sql +=" group by date";
			
			stopActivityHandler.setIndexNumberPassengerOn(1);
			stopActivityHandler.setIndexNumberPassengerOff(2);
			stopActivityHandler.setIndexDate(3);
			
			stopActivities = stopActivityHandler.queryStopActivity(sql);
	        
		}
		catch(Exception ex)
		{
			System.out.println("getPassengerPerDay:" + ex.getMessage());
		}
		
		return stopActivities;
	}
	
	
	
}
