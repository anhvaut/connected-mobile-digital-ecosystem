package uow.cmde.transim.historydata.report;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.StringTokenizer;

import uow.cmde.transim.transit.model.*;
import uow.cmde.transim.transit.model.impl.Stop;
import uow.cmde.transim.transit.model.impl.StopActivities;
import uow.cmde.transim.transit.model.impl.StopActivity;
import uow.cmde.transim.util.osm.MapPosition;
import uow.cmde.transim.util.sql.MySqlHandler;

public class APCDataHandler {

	private static String dbName = "avl_apc_db";
	private static String dbUser = "root";
	private static String dbPass = "";
	
	public static void main(String[] args) {
		
		
		Calendar myDate = Calendar.getInstance();
		
		int[] timeTableValue ={8*60 + 6, 8*60 +  26, 8*60 +  47, 8*60 +  59, 9*60 +  13, 9*60 +  25, 9*60 +  39, 10*60 
				          , 10*60 +  19, 10*60 +  36, 12*60 +  19, 12*60 +  35, 12*60 +  50, 13*60 +  10, 14*60 +  1
				          , 14*60 +  17, 14*60 +  33, 15*60 +  17, 15*60 +  34, 15*60 +  49, 16*60 +  2, 16*60 +  17
				          , 16*60 +  37, 16*60 + 49, 17*60 +  2,17*60 +  15, 17*60 +  27, 17*60 +  39, 18*60 +  1
				          , 18*60 +  16, 18*60 +  29, 18*60 +  52}; 
		
		String[] timeTable = {"08:06", "08:26", "08:47", "08:59", "09:13", "09:25", "09:39", "10:60" 
		          			, "10:19", "10:36", "12:19", "12:35", "12:50", "13:10", "14:01"
		          			, "14:17", "14:33", "15:17", "15:34", "15:49", "16:02", "16:17"
		          			, "16:37", "16:49", "17:02","17:15", "17:27", "17:39", "18:01"
		          			, "18:16", "18:29", "18:52"};
		
		//convert raw apc data to stop information
		try
		{
			MapPosition trainStationPosition = new MapPosition(Float.parseFloat("-34.412376"), Float.parseFloat("150.891129"));
			MapPosition northfieldAvePosition = new MapPosition(Float.parseFloat("-34.408070"), Float.parseFloat("150.878387"));
			
			MySqlHandler mySqlHandler = new MySqlHandler();
			
			mySqlHandler.setDatabase(dbName);
			mySqlHandler.setUsername(dbUser);
			mySqlHandler.setPassword(dbPass);
			
			//String sql = "select longitude, latitude, time_stamp, on_off, MACAddress from buspassengermac where bus_id = 'unishuttle02' and length(MACAddress)<3 and time_stamp like '2012-05-28%'";
			String sql ="select longitude, latitude, time_stamp, on_off, MACAddress from buspassengermac where bus_id = 'unishuttle02' and length(MACAddress)<3 and time_stamp between '2012-05-20' and '2012-05-26' and longitude <> 'nan'  and latitude <> 'nan' order by time_stamp asc ";
			mySqlHandler.setQuery(sql);
	        ResultSet rs = mySqlHandler.executeQuery();
	        
	        StopActivities stopActivities = new StopActivities();
	        
	        while(rs.next())
	        {
	        	String stopName = "";
	        	String date = "";
	        	String time = "";
	        	int number_passenger_on = 0;
	        	int number_passenger_off = 0;
	        	float longitude = Float.parseFloat(rs.getString(1));
	        	float latitude = Float.parseFloat(rs.getString(2));
	        	String time_stamp = rs.getString(3);
	        	
	        	double distanceTrainStation=trainStationPosition.getDistKM(trainStationPosition.getLat(), trainStationPosition.getLon(), latitude, longitude);
	        	double distanceNorthfieldAve=trainStationPosition.getDistKM(northfieldAvePosition.getLat(), northfieldAvePosition.getLon(), latitude, longitude);
	        	
	        	if((int)(distanceTrainStation*1000)<100)
	        	{
	        		stopName = "Train Station";
	        	}
	        	else if((int)(distanceNorthfieldAve*1000) <100)
	        	{
	        		stopName = "Northfield Ave";
	        	}
	        	
	        	if(rs.getString(4).trim().equals("1"))
	        	{
	        		number_passenger_on = Integer.parseInt(rs.getString(5));
	        	}
	        	else
	        	{
	        		number_passenger_off = Integer.parseInt(rs.getString(5));
	        	}
	        	
	        	StringTokenizer timeStampTokenizer = new  StringTokenizer(time_stamp," ");
	        	date = timeStampTokenizer.nextToken();
	        	time  = timeStampTokenizer.nextToken();
	        			
	        	StringTokenizer timeTokenizer = new  StringTokenizer(time,":");
	        	
	        	int myTime = Integer.parseInt(timeTokenizer.nextToken()) * 60 + Integer.parseInt(timeTokenizer.nextToken());
	        	
	        	time = timeTable[0];
	        	for(int i=timeTableValue.length - 1;i>=0;i--)
	        	{
	        		if(myTime>timeTableValue[i])
	        		{
	        			time = timeTable[i];
	        			break;
	        		}
	        	}
	        	
	        	
	        	StringTokenizer dateTokenizer = new  StringTokenizer(date,"-");
	        	myDate.set(Integer.parseInt(dateTokenizer.nextToken()),Integer.parseInt(dateTokenizer.nextToken())-1,Integer.parseInt(dateTokenizer.nextToken()));
	        	int weekday = myDate.get(Calendar.DAY_OF_WEEK);
	        	
	        	String dayOfWeek = "";
				if (weekday == 2) dayOfWeek = "Monday";
				else if (weekday == 3) dayOfWeek = "Tuesday";
				else if (weekday == 4) dayOfWeek = "Wednesday";
				else if (weekday == 5) dayOfWeek = "Thursday";
				else if (weekday == 6) dayOfWeek = "Friday";
				
	        	if(!stopName.equals(""))
	        	{
	        		//System.out.println("Stop Name:" + stopName + "--date:" + date + "--weekday:" + weekday );
	        		StopActivity stopActivity = new StopActivity();
		        	stopActivity.setTimeStamp(time_stamp);
		        	stopActivity.setDate(dayOfWeek + " " +date);
		        	stopActivity.setArrivalTime(time);
		        	Stop s = new Stop();
		        	s.setStopName(stopName);
		        	stopActivity.setStop(s);
		        	stopActivity.setNumberPassengerOn(number_passenger_on);
		        	stopActivity.setNumberPassengerOff(number_passenger_off);
		        	
		        	stopActivities.addStopActivity(stopActivity);
	        	}
	        	
	        	
	        	
	        	
	        }
	        
	        ///Insert data
	       
			String insertSql = "INSERT INTO apcdataprocessed(time_stamp,date, time,stop_name,number_passenger_on,number_passenger_off)";
			insertSql += " VALUES(?,?,?,?,?,? )";
	        
	        mySqlHandler.setQuery(insertSql);
			
	        int i=0;
	        for(IStopActivity stopActivity:stopActivities.getAllStopActivities())
	        {
	        	mySqlHandler.getPreparedStatement().setString(1, stopActivity.getTimeStamp());
	        	mySqlHandler.getPreparedStatement().setString(2, stopActivity.getDate());
	        	mySqlHandler.getPreparedStatement().setString(3, stopActivity.getArrivalTime());
				mySqlHandler.getPreparedStatement().setString(4, stopActivity.getStop().getStopName());
				mySqlHandler.getPreparedStatement().setString(5, "" + stopActivity.getNumberPassengerOn());
				mySqlHandler.getPreparedStatement().setString(6, "" + stopActivity.getNumberPassengerOff());
				
				mySqlHandler.executeUpdate();
				
				System.out.println("running..." + i++);
				
	        }
	        
	    	mySqlHandler.close();
	    	
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
