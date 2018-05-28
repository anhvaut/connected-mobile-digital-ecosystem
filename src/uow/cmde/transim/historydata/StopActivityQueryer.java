package uow.cmde.transim.historydata;

import java.sql.ResultSet;

import uow.cmde.transim.transit.model.impl.Stop;
import uow.cmde.transim.transit.model.impl.StopActivities;
import uow.cmde.transim.transit.model.impl.StopActivity;
import uow.cmde.transim.util.sql.MySqlHandler;
import uow.cmde.transim.util.*;

/**
 * 
 * @author Vu The Tran
 * @since 28/12/2011
 */
public class StopActivityQueryer {

	private int indexNumberPassengerOn = -1;
	private int indexNumberPassengerOff = -1;
	private int indexStopName = -1;
	private int indexArrivalTime = -1;
	private int indexDepartureTime = -1;
	private int indexDate = -1;
	private MySqlHandler mySqlHandler;
	
	public StopActivityQueryer(){
		mySqlHandler = new MySqlHandler();
		mySqlHandler.setHost(AppConfig.DATABASE_HOST);
		mySqlHandler.setDatabase(AppConfig.DATABASE_SOURCE);
		mySqlHandler.setUsername(AppConfig.DATABASE_USERNAME);
		mySqlHandler.setPassword(AppConfig.DATABASE_PASSWORD);
		
	}
	
	/**
	 * queryStopActivity
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public StopActivities queryStopActivity(String sql) throws Exception
	{
			StopActivities stopActivities = new StopActivities();
		
			
	        mySqlHandler.setQuery(sql);
	        ResultSet rs = mySqlHandler.executeQuery();
	        while(rs.next())
	        {
	        	Stop aStop =new Stop();
	        	StopActivity stopActivity = new StopActivity();
	        	
	        	
	        	if(indexNumberPassengerOn !=-1)
	        	{
	        		stopActivity.setNumberPassengerOn(Integer.parseInt(rs.getString(indexNumberPassengerOn)));
	        		//System.out.println("on:" + indexNumberPassengerOn);
	        	}
	        	
	        	if(indexNumberPassengerOff !=-1)
	        	{
	        		stopActivity.setNumberPassengerOff(Integer.parseInt(rs.getString(indexNumberPassengerOff)));
	        		//System.out.println("off" + indexNumberPassengerOff);
	        	}
	        	
	        	if(indexDate !=-1)
	        	{
	        		stopActivity.setDate(rs.getString(indexDate));
	        		stopActivity.setDayOfWeek(rs.getString(indexDate));
	        		//System.out.println("date:" + indexDate);
	        	}
	        	
	        	if(indexArrivalTime !=-1)
	        	{
	        		stopActivity.setArrivalTime(rs.getString(indexArrivalTime));
	        		//System.out.println("arrival time:" + indexArrivalTime);
	        	}
	        	
	        	if(indexDepartureTime !=-1)
	        	{
	        		stopActivity.setDepartureTime(rs.getString(indexDepartureTime));
	        		//System.out.println("departureTime" + indexNumberPassengerOn);
	        	}
	        	
	        	
	        	if(indexStopName !=-1)
	        	{
	        		aStop.setStopName(rs.getString(indexStopName));
	        	}
	        	
	        	
	        	
	        	stopActivity.setStop(aStop);
	        	stopActivities.addStopActivity(stopActivity);
	        	
	        }
	        
	        mySqlHandler.close();
	   
		
		return stopActivities;
	}
	
	/**
	 * setIndexNumberPassengerOn
	 * @param indexNumberPassengerOn
	 */
	public void setIndexNumberPassengerOn(int indexNumberPassengerOn)
	{
		this.indexNumberPassengerOn = indexNumberPassengerOn;
	}
	
	/**
	 * setIndexNumberPassengerOff
	 * @param indexNumberPassengerOff
	 */
	public void setIndexNumberPassengerOff(int indexNumberPassengerOff)
	{
		this.indexNumberPassengerOff = indexNumberPassengerOff;
	}
	
	/**
	 * setIndexStopName
	 * @param indexStopName
	 */
	public void setIndexStopName(int indexStopName)
	{
		this.indexStopName = indexStopName;
	}
	
	/**
	 * setIndexArrivalTime
	 * @param indexArrivalTime
	 */
	public void setIndexArrivalTime(int indexArrivalTime)
	{
		this.indexArrivalTime = indexArrivalTime;
	}
	
	/**
	 * setIndexDepartureTime
	 * @param indexDepartureTime
	 */
	public void setIndexDepartureTime(int indexDepartureTime)
	{
		this.indexDepartureTime = indexDepartureTime;
	}
	
	/**
	 * setIndexDate
	 * @param indexDate
	 */
	public void setIndexDate(int indexDate)
	{
		this.indexDate = indexDate;
	}
	
	public void setDatabaseInfo(String dbName, String dbUser, String dbPass)
	{
		mySqlHandler.setDatabase(dbName);
		mySqlHandler.setUsername(dbUser);
		mySqlHandler.setPassword(dbPass);
		
	}
	
}
