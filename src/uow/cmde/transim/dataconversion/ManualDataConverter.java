package uow.cmde.transim.dataconversion;


import uow.cmde.transim.util.sql.*;
import com.csvreader.CsvReader;

public class ManualDataConverter {

	/**
	 * convertOldData
	 * @param filePath
	 */
	public static void convertOldData(String filePath)
	{
		try
		{
			CsvReader stopActivities = new CsvReader(filePath);
			stopActivities.readHeaders();
			
			MySqlHandler mySqlHandler = new MySqlHandler();
			
			String sql = "INSERT INTO manual_stop_activity(date,trip_id,bus_type,time,stop_code,stop_name,number_passenger_on,number_passenger_off)";
	              sql += " VALUES(?,?,?,?,?,?,?,? )";
	        
	        mySqlHandler.setQuery(sql);
			
			int i=0;
			while (stopActivities.readRecord())
			{
				
				mySqlHandler.getPreparedStatement().setString(1, stopActivities.get("date"));
				mySqlHandler.getPreparedStatement().setString(2, stopActivities.get("trip_id"));
				mySqlHandler.getPreparedStatement().setString(3, stopActivities.get("bus_type"));
				mySqlHandler.getPreparedStatement().setString(4, stopActivities.get("time"));
				mySqlHandler.getPreparedStatement().setString(5, stopActivities.get("stop_code"));
				mySqlHandler.getPreparedStatement().setString(6, stopActivities.get("stop_name"));
				mySqlHandler.getPreparedStatement().setString(7, stopActivities.get("number_passenger_on"));
				mySqlHandler.getPreparedStatement().setString(8, stopActivities.get("number_passenger_off"));
				
				mySqlHandler.executeUpdate();
				
				System.out.println("running..." + i++);
				
				
			}
			
			mySqlHandler.close();
			
			stopActivities.close();
			
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public static void main(String[] args) {
		//ManualDataConverter.convertOldData("D:\\My Research\\Open data AVH & APC\\Wollongong data\\2011\\GK Shuttle - Stats for week - 12-16 Sept 2011.csv");
		//ManualDataConverter.convertOldData("D:\\My Research\\Open data AVH & APC\\Wollongong data\\2011\\GK Shuttle - Stats for week - 04-08 April - 2011.csv");
		//ManualDataConverter.convertOldData("D:\\My Research\\Open data AVH & APC\\Wollongong data\\2011\\GK Shuttle - Stats for week - 14-18 March 2011.csv");
		//ManualDataConverter.convertOldData("D:\\My Research\\Open data AVH & APC\\Wollongong data\\2011\\GK Shuttle - Stats for week - 28 Feb - 04 March - 2011.csv");
	}
	
	
	
}
