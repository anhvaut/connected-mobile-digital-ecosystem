package uow.cmde.transim.transit.outputanalysis;

import java.sql.ResultSet;
import java.util.*;
import java.text.*;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.stat.inference.TestUtils;


import uow.cmde.transim.util.TimeConverter;
import uow.cmde.transim.util.osm.MapPosition;
import uow.cmde.transim.util.sql.MySqlHandler;
import uow.cmde.transim.transit.model.IShape;
import uow.cmde.transim.transit.model.impl.*;

public class RealWorldData {

	List<IShape> realShapes= new ArrayList<IShape>();
	Shape sKidsUni = new Shape();
	Shape sScienceBld = new Shape();
	Shape sCreativeArtsBld = new Shape();
	Shape sHopeTheatre = new Shape();
	Shape sRobsonsRd = new Shape();
	Shape sReserveSt = new Shape();
	Shape sGreenacreRd = new Shape();
	Shape sWeeronaThrosbyDr = new Shape();
	Shape sKeiraview = new Shape();
	Shape sThrosbyDr = new Shape();
	Shape sWisemanPark = new Shape();
	Shape sEasternSt = new Shape();
	
	MySqlHandler mySqlHandler = new MySqlHandler();
	
	public RealWorldData()
	{
		
		
		mySqlHandler.setHost("localhost");
		mySqlHandler.setDatabase("buslocation");
		mySqlHandler.setUsername("root");
		mySqlHandler.setPassword("");
		
		
		//KidsUni 150.882797 -34.407879
		sKidsUni.setLon(150.882797);
		sKidsUni.setLat(-34.407879);
		realShapes.add(sKidsUni);
		
		//ScienceBld 150.880234 -34.404812
		sScienceBld.setLon(150.880234);
		sScienceBld.setLat(-34.404812);
		realShapes.add(sScienceBld);
		
		//CreativeArtsBld 150.877472 -34.403828
		sCreativeArtsBld.setLon(150.877472);
		sCreativeArtsBld.setLat(-34.403828);
		realShapes.add(sCreativeArtsBld);
		
		//HopeTheatre 150.875977 -34.40588
		sHopeTheatre.setLon(150.875977);
		sHopeTheatre.setLat(-34.40588);
		realShapes.add(sHopeTheatre);
		
		//RobsonsRd 150.870544 -34.413761
		sRobsonsRd.setLon(150.870544);
		sRobsonsRd.setLat(-34.413761);
		realShapes.add(sRobsonsRd);
		
		//ReserveSt 150.869461 -34.419758
		sReserveSt.setLon(150.869461);
		sReserveSt.setLat(-34.419758);
		realShapes.add(sReserveSt);
		
		//GreenacreRd 150.878433 -34.420189
		sGreenacreRd.setLon(150.878433);
		sGreenacreRd.setLat(-34.420189);
		realShapes.add(sGreenacreRd);
		
		//WeeronaThrosbyDr 150.887207 -34.420238
		sWeeronaThrosbyDr.setLon(150.887207);
		sWeeronaThrosbyDr.setLat(-34.420238);
		realShapes.add(sWeeronaThrosbyDr);
		
		//Keiraview 150.893997 -34.420792
		sKeiraview.setLon(150.893997);
		sKeiraview.setLat(-34.420792);
		realShapes.add(sKeiraview);
		
		//ThrosbyDr 150.887344 -34.42038
		sThrosbyDr.setLon(150.887344);
		sThrosbyDr.setLat(-34.42038);
		realShapes.add(sThrosbyDr);
		
		//WisemanPark 150.883698 -34.417362
		sWisemanPark.setLon(150.883698);
		sWisemanPark.setLat(-34.417362);
		realShapes.add(sWisemanPark);
		
		//EasternSt 150.879395 -34.414005
		sEasternSt.setLon(150.879395);
		sEasternSt.setLat(-34.414005);
		realShapes.add(sEasternSt);
		
	}
	public static void main(String[] args)
	{
		RealWorldData rw = new RealWorldData();
		rw.calculateDwellTime();
		rw.calculateTripTime();
		rw.calculateSchedule();
	}
	
	public void calculateSchedule()
	{
		
		String[] arrivalTime1 = new String[18];
		arrivalTime1[1] = "08:19:00";
		arrivalTime1[2] = "08:49:00";
		arrivalTime1[3] = "09:19:00";
		arrivalTime1[4] = "09:49:00";
		arrivalTime1[5] = "10:19:00";
		arrivalTime1[6] = "16:34:00";
		arrivalTime1[7] = "17:04:00";
		arrivalTime1[8] = "17:34:00";
		arrivalTime1[9] = "18:04:00";
		arrivalTime1[10] = "18:34:00";
		arrivalTime1[11] = "19:04:00";
		arrivalTime1[12] = "19:34:00";
		arrivalTime1[13] = "20:04:00";
		arrivalTime1[14] = "20:34:00";
		arrivalTime1[15] = "21:04:00";
		arrivalTime1[16] = "21:34:00";
		arrivalTime1[17] = "22:04:00";
		
		String[] arrivalTime2 = new String[18];
		arrivalTime2[1] = "08:21:00";
		arrivalTime2[2] = "08:51:00";
		arrivalTime2[3] = "09:21:00";
		arrivalTime2[4] = "09:51:00";
		arrivalTime2[5] = "16:36:00";
		arrivalTime2[6] = "17:06:00";
		arrivalTime2[7] = "17:36:00";
		arrivalTime2[8] = "18:06:00";
		arrivalTime2[9] = "18:36:00";
		arrivalTime2[10] = "19:06:00";
		arrivalTime2[11] = "19:36:00";
		arrivalTime2[12] = "20:06:00";
		arrivalTime2[13] = "20:36:00";
		arrivalTime2[14] = "21:06:00";
		arrivalTime2[15] = "21:36:00";
		arrivalTime2[16] = "22:06:00";
		arrivalTime2[17] = "00:00:00";
		
		String[] arrivalTime3 = new String[18];
		arrivalTime3[1] = "08:23:00";
		arrivalTime3[2] = "08:53:00";
		arrivalTime3[3] = "09:23:00";
		arrivalTime3[4] = "09:53:00";
		arrivalTime3[5] = "16:38:00";
		arrivalTime3[6] = "17:08:00";
		arrivalTime3[7] = "17:38:00";
		arrivalTime3[8] = "18:08:00";
		arrivalTime3[9] = "18:38:00";
		arrivalTime3[10] = "19:08:00";
		arrivalTime3[11] = "19:38:00";
		arrivalTime3[12] = "20:08:00";
		arrivalTime3[13] = "20:38:00";
		arrivalTime3[14] = "21:08:00";
		arrivalTime3[15] = "21:38:00";
		arrivalTime3[16] = "22:08:00";
		arrivalTime3[17] = "00:00:00";
		
		
		String[] arrivalTime4 = new String[18];
		arrivalTime4[1] = "08:25:00";
		arrivalTime4[2] = "08:55:00";
		arrivalTime4[3] = "09:25:00";
		arrivalTime4[4] = "09:55:00";
		arrivalTime4[5] = "16:40:00";
		arrivalTime4[6] = "17:10:00";
		arrivalTime4[7] = "17:40:00";
		arrivalTime4[8] = "18:10:00";
		arrivalTime4[9] = "18:40:00";
		arrivalTime4[10] = "19:10:00";
		arrivalTime4[11] = "19:40:00";
		arrivalTime4[12] = "20:10:00";
		arrivalTime4[13] = "20:40:00";
		arrivalTime4[14] = "21:10:00";
		arrivalTime4[15] = "21:40:00";
		arrivalTime4[16] = "22:10:00";
		arrivalTime4[17] = "00:00:00";
		
		String[] arrivalTime5 = new String[18];
		arrivalTime5[1] = "07:58:00";
		arrivalTime5[2] = "08:28:00";
		arrivalTime5[3] = "08:58:00";
		arrivalTime5[4] = "09:28:00";
		arrivalTime5[5] = "09:58:00";
		arrivalTime5[6] = "16:43:00";
		arrivalTime5[7] = "17:13:00";
		arrivalTime5[8] = "17:43:00";
		arrivalTime5[9] = "18:13:00";
		arrivalTime5[10] = "18:43:00";
		arrivalTime5[11] = "19:13:00";
		arrivalTime5[12] = "19:43:00";
		arrivalTime5[13] = "20:13:00";
		arrivalTime5[14] = "20:43:00";
		arrivalTime5[15] = "21:13:00";
		arrivalTime5[16] = "21:43:00";
		arrivalTime5[17] = "22:13:00";
		
		String[] arrivalTime6 = new String[18];
		arrivalTime6[1] = "08:00:00";
		arrivalTime6[2] = "08:30:00";
		arrivalTime6[3] = "09:00:00";
		arrivalTime6[4] = "09:30:00";
		arrivalTime6[5] = "10:00:00";
		arrivalTime6[6] = "16:45:00";
		arrivalTime6[7] = "17:15:00";
		arrivalTime6[8] = "17:45:00";
		arrivalTime6[9] = "18:15:00";
		arrivalTime6[10] = "18:45:00";
		arrivalTime6[11] = "19:15:00";
		arrivalTime6[12] = "19:45:00";
		arrivalTime6[13] = "20:15:00";
		arrivalTime6[14] = "20:45:00";
		arrivalTime6[15] = "21:15:00";
		arrivalTime6[16] = "21:45:00";
		arrivalTime6[17] = "22:15:00";
		
		
		String[] arrivalTime7 = new String[18];
		arrivalTime7[1] = "08:03:00";
		arrivalTime7[2] = "08:33:00";
		arrivalTime7[3] = "09:03:00";
		arrivalTime7[4] = "09:33:00";
		arrivalTime7[5] = "10:03:00";
		arrivalTime7[6] = "16:48:00";
		arrivalTime7[7] = "17:18:00";
		arrivalTime7[8] = "17:48:00";
		arrivalTime7[9] = "18:18:00";
		arrivalTime7[10] = "18:48:00";
		arrivalTime7[11] = "19:18:00";
		arrivalTime7[12] = "19:48:00";
		arrivalTime7[13] = "20:18:00";
		arrivalTime7[14] = "20:48:00";
		arrivalTime7[15] = "21:18:00";
		arrivalTime7[16] = "21:48:00";
		arrivalTime7[17] = "22:18:00";
		
		String[] arrivalTime8 = new String[18];
		arrivalTime8[1] = "08:05:00";
		arrivalTime8[2] = "08:35:00";
		arrivalTime8[3] = "09:05:00";
		arrivalTime8[4] = "09:35:00";
		arrivalTime8[5] = "10:05:00";
		arrivalTime8[6] = "16:50:00";
		arrivalTime8[7] = "17:20:00";
		arrivalTime8[8] = "17:50:00";
		arrivalTime8[9] = "18:20:00";
		arrivalTime8[10] = "18:50:00";
		arrivalTime8[11] = "19:20:00";
		arrivalTime8[12] = "19:50:00";
		arrivalTime8[13] = "20:20:00";
		arrivalTime8[14] = "20:50:00";
		arrivalTime8[15] = "21:20:00";
		arrivalTime8[16] = "21:50:00";
		arrivalTime8[17] = "22:20:00";
		
		String[] arrivalTime9 = new String[18];
		arrivalTime9[1] = "08:09:00";
		arrivalTime9[2] = "08:39:00";
		arrivalTime9[3] = "09:09:00";
		arrivalTime9[4] = "09:39:00";
		arrivalTime9[5] = "10:09:00";
		arrivalTime9[6] = "16:54:00";
		arrivalTime9[7] = "17:24:00";
		arrivalTime9[8] = "17:54:00";
		arrivalTime9[9] = "18:24:00";
		arrivalTime9[10] = "18:54:00";
		arrivalTime9[11] = "19:24:00";
		arrivalTime9[12] = "19:54:00";
		arrivalTime9[13] = "20:24:00";
		arrivalTime9[14] = "20:54:00";
		arrivalTime9[15] = "21:24:00";
		arrivalTime9[16] = "21:54:00";
		arrivalTime9[17] = "22:24:00";
		
		String[] arrivalTime10 = new String[18];
		arrivalTime10[1] = "08:13:00";
		arrivalTime10[2] = "08:43:00";
		arrivalTime10[3] = "09:13:00";
		arrivalTime10[4] = "09:43:00";
		arrivalTime10[5] = "10:13:00";
		arrivalTime10[6] = "16:58:00";
		arrivalTime10[7] = "17:28:00";
		arrivalTime10[8] = "17:58:00";
		arrivalTime10[9] = "18:28:00";
		arrivalTime10[10] = "18:58:00";
		arrivalTime10[11] = "19:28:00";
		arrivalTime10[12] = "19:58:00";
		arrivalTime10[13] = "20:28:00";
		arrivalTime10[14] = "20:58:00";
		arrivalTime10[15] = "21:28:00";
		arrivalTime10[16] = "22:28:00";
		arrivalTime10[17] = "22:28:00";
		
		
		String[] arrivalTime11 = new String[18];
		arrivalTime11[1] = "08:15:00";
		arrivalTime11[2] = "08:45:00";
		arrivalTime11[3] = "09:15:00";
		arrivalTime11[4] = "09:45:00";
		arrivalTime11[5] = "10:15:00";
		arrivalTime11[6] = "17:00:00";
		arrivalTime11[7] = "17:30:00";
		arrivalTime11[8] = "18:00:00";
		arrivalTime11[9] = "18:30:00";
		arrivalTime11[10] = "19:00:00";
		arrivalTime11[11] = "19:30:00";
		arrivalTime11[12] = "20:00:00";
		arrivalTime11[13] = "20:30:00";
		arrivalTime11[14] = "21:00:00";
		arrivalTime11[15] = "21:30:00";
		arrivalTime11[16] = "22:00:00";
		arrivalTime11[17] = "22:30:00";
		
		String[] arrivalTime12 = new String[18];
		arrivalTime12[1] = "08:17:00";
		arrivalTime12[2] = "08:47:00";
		arrivalTime12[3] = "09:17:00";
		arrivalTime12[4] = "09:47:00";
		arrivalTime12[5] = "10:17:00";
		arrivalTime12[6] = "17:02:00";
		arrivalTime12[7] = "17:32:00";
		arrivalTime12[8] = "18:02:00";
		arrivalTime12[9] = "18:32:00";
		arrivalTime12[10] = "19:02:00";
		arrivalTime12[11] = "19:32:00";
		arrivalTime12[12] = "20:02:00";
		arrivalTime12[13] = "20:32:00";
		arrivalTime12[14] = "21:02:00";
		arrivalTime12[15] = "21:32:00";
		arrivalTime12[16] = "22:02:00";
		arrivalTime12[17] = "22:32:00";
		
		MySqlHandler mySqlHandler = new MySqlHandler();
		mySqlHandler.setHost("localhost");
		mySqlHandler.setDatabase("buslocation");
		mySqlHandler.setUsername("root");
		mySqlHandler.setPassword("");
		
		SummaryStatistics statsSchedule = new SummaryStatistics();
		
		String sql="SELECT * FROM buslocation WHERE bus_id = 'unishuttle04'";
		
		
		try
		{
			mySqlHandler.setQuery(sql);
	        ResultSet rs = mySqlHandler.executeQuery();
	        
	        int previousStop = -1;
	        String dateStart="";
    		String dateStop="";
    		
    		// Custom date format
    		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    		
	        while(rs.next())
	        {
	        	if(!rs.getString("longitude").trim().equals("nan") && !rs.getString("latitude").trim().equals("nan"))
	        	{
	        		double lon = rs.getDouble("longitude");
	        		double lat = rs.getDouble("latitude");
	        		MapPosition position = new MapPosition((float)lat, (float)lon);
	        		
	        		int i = 1;
	        		for(IShape s:realShapes)
	        		{
	        			
	        			double m = position.getDistKM((float)lat, (float)lon,(float)s.getLat(),(float)s.getLon())*1000;
	        			
	        			if(m < 100)
	        			{
	        				
	        				
	        				//System.out.println("stop:" + i + " --distance:" + m + " --time:" + rs.getString("time_stamp"));
	        				
	        				if(previousStop == i )
	        				{
	        					if(dateStart.equals(""))
	        					{
	        						dateStart = rs.getString("time_stamp");
	        					}
	        					
	        					
	        				}
	        				else
	        				{
	        					
	        					if(!dateStart.equals(""))
	        					{
		        					//System.out.println("timing stop:" + previousStop + " start:" + dateStart.substring(11,dateStart.length()-2) );
		        					
	        						dateStart = dateStart.substring(11,dateStart.length()-2);
		        					switch(previousStop)
	        						{
	        							case 1:
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime1[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        							case 2:
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime2[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        							case 3:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime3[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        							
	        								break;
	        							case 4:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime4[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        							case 5:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime5[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        							
	        							case 6:
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime6[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        							case 7:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime7[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        							case 8:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime8[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        							case 9:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime9[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        							case 10:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime10[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        							case 11:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime11[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        							case 12:
	        								
	        								for(int l=1;l<=17;l++)
	        								{
	        									
	        									double minutes = Math.abs((TimeConverter.convertTimeToSecond(arrivalTime12[l]) - TimeConverter.convertTimeToSecond(dateStart))/60.0);
	        									if(minutes<5 && minutes > 0)
	        									{
	        										//System.out.println("timing stop:" + previousStop + " schedule:" + minutes );
	        										statsSchedule.addValue(minutes);
	        									}
	        									 
	        								}
	        								break;
	        								
	        						}
	        					}
	        					
	        					dateStart = "";
	        					//dateStop = rs.getString("time_stamp");
	        					
	        					previousStop = i;
	        					
	        				}
	        					
	        				
	        			}
	        			
	        			i++;
	        		}
	        	
	        		
	        	}
	        	
	        }
	        
	        
	        System.out.println("Realworld Schedule validation ....");
	        System.out.println("Mean | Stdev | Max | Min  | Obs");
	        System.out.println(statsSchedule.getMean() + " | " + statsSchedule.getStandardDeviation() + " | " + statsSchedule.getMax() + " | " + statsSchedule.getMin() + " | " + statsSchedule.getN() );
	      
	        
	        System.out.println("..............................");
	        
	      
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	
	public void calculateTripTime()
	{
			
		
		String sql="SELECT * FROM buslocation WHERE bus_id = 'unishuttle04'";
		
		
		try
		{
			mySqlHandler.setQuery(sql);
	        ResultSet rs = mySqlHandler.executeQuery();
	        
	        int previousStop = -1;
	        String dateStart="";
    		String dateStop="";
    		
    		// Custom date format
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		SummaryStatistics statsTripTime = new SummaryStatistics();
    		
	        while(rs.next())
	        {
	        	if(!rs.getString("longitude").trim().equals("nan") && !rs.getString("latitude").trim().equals("nan"))
	        	{
	        		double lon = rs.getDouble("longitude");
	        		double lat = rs.getDouble("latitude");
	        		MapPosition position = new MapPosition((float)lat, (float)lon);
	        		
	        		int i = 1;
	        		for(IShape s:realShapes)
	        		{
	        			
	        			double m = position.getDistKM((float)lat, (float)lon,(float)s.getLat(),(float)s.getLon())*1000;
	        			//double m = position.getDistKM((float)lat, (float)lon,(float)lat,(float)lon)*1000;
	        			if(m < 200)
	        			{
	        				
	        				//System.out.println(lon + " $ " + lat);
	        				//System.out.println("stop:" + i + " --distance:" + m + " --time:" + rs.getString("time_stamp"));
	        				
	        				if( i == 1 )
	        				{
	        					
	        					dateStart = rs.getString("time_stamp");
	        					
	        				}
	        				else if (i==12)
	        				{
	        					double diffMinutes = 0;
	        					
	        					dateStop = rs.getString("time_stamp");
	        					
	        					if(!dateStart.equals("") && !dateStop.equals(""))
	        					{
		        					Date d1 = format.parse(dateStart);
		        					Date d2 = format.parse(dateStop);   
		        					diffMinutes = ((d2.getTime() - d1.getTime())/1000)/60.0;
	        					}
	        					
	        					
	        					if(diffMinutes <=40 && diffMinutes >=15)
	        					{
	        					
	        						statsTripTime.addValue(diffMinutes);
	        						//System.out.println("timing stop:" + i + "Start:" + dateStart + "Stop:" + dateStop + " trip time:" + diffMinutes);
	        					}
	        					
	        					dateStart = "";
	        					dateStop = "";
	        					
	        					
	        				}
	        					
	        				
	        			}
	        			
	        			i++;
	        		}
	        	
	        		
	        	}
	        	
	        }
	        
	        
	        System.out.println("Realworld Trip time validation ....");
	        System.out.println("Mean | Stdev | Max | Min  | Obs");
	        System.out.println(statsTripTime.getMean() + " | " + statsTripTime.getStandardDeviation() + " | " + statsTripTime.getMax() + " | " + statsTripTime.getMin() + " | " + statsTripTime.getN() );
	      
	        
	        System.out.println("..............................");
	       
	        
	        
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public void calculateDwellTime()
	{
		
		SummaryStatistics statsDwellTime1 = new SummaryStatistics();
		SummaryStatistics statsDwellTime2 = new SummaryStatistics();
		SummaryStatistics statsDwellTime3 = new SummaryStatistics();
		SummaryStatistics statsDwellTime4 = new SummaryStatistics();
		SummaryStatistics statsDwellTime5 = new SummaryStatistics();
		SummaryStatistics statsDwellTime6 = new SummaryStatistics();
		SummaryStatistics statsDwellTime7 = new SummaryStatistics();
		SummaryStatistics statsDwellTime8 = new SummaryStatistics();
		SummaryStatistics statsDwellTime9 = new SummaryStatistics();
		SummaryStatistics statsDwellTime10 = new SummaryStatistics();
		SummaryStatistics statsDwellTime11 = new SummaryStatistics();
		SummaryStatistics statsDwellTime12 = new SummaryStatistics();
		
		SummaryStatistics statsModel1 = new SummaryStatistics();
	
		
		String sql="SELECT * FROM buslocation WHERE bus_id = 'unishuttle04' or bus_id = 'unishuttle05'";
		
		
		try
		{
			mySqlHandler.setQuery(sql);
	        ResultSet rs = mySqlHandler.executeQuery();
	        
	        int previousStop = -1;
	        String dateStart="";
    		String dateStop="";
    		
    		// Custom date format
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
	        while(rs.next())
	        {
	        	if(!rs.getString("longitude").trim().equals("nan") && !rs.getString("latitude").trim().equals("nan"))
	        	{
	        		double lon = rs.getDouble("longitude");
	        		double lat = rs.getDouble("latitude");
	        		MapPosition position = new MapPosition((float)lat, (float)lon);
	        		
	        		int i = 1;
	        		for(IShape s:realShapes)
	        		{
	        			
	        			double m = position.getDistKM((float)lat, (float)lon,(float)s.getLat(),(float)s.getLon())*1000;
	        			//double m = position.getDistKM((float)lat, (float)lon,(float)lat,(float)lon)*1000;
	        			if(m < 100)
	        			{
	        				
	        				//System.out.println(lon + " $ " + lat);
	        				//System.out.println("stop:" + i + " --distance:" + m + " --time:" + rs.getString("time_stamp"));
	        				
	        				if(previousStop == i )
	        				{
	        					if(dateStart.equals(""))
	        					{
	        						dateStart = rs.getString("time_stamp");
	        					}
	        					dateStop = rs.getString("time_stamp");
	        				}
	        				else
	        				{
	        					double diffMinutes = 0;
	        					
	        					
	        					if(!dateStart.equals("") && !dateStop.equals(""))
	        					{
		        					Date d1 = format.parse(dateStart);
		        					Date d2 = format.parse(dateStop);   
		        					diffMinutes = ((d2.getTime() - d1.getTime())/1000)/60.0;
	        					}
	        					
	        					if(diffMinutes<50)
	        					{
	        						switch(previousStop)
	        						{
	        							case 1:
	        								statsDwellTime1.addValue(diffMinutes);
	        								
	        							case 2:
	        								statsDwellTime2.addValue(diffMinutes);
	        								
	        							case 3:
	        								statsDwellTime3.addValue(diffMinutes);
	        								
	        							case 4:
	        								statsDwellTime4.addValue(diffMinutes);
	        								
	        							case 5:
	        								statsDwellTime5.addValue(diffMinutes);
	        								
	        							case 6:
	        								statsDwellTime6.addValue(diffMinutes);
	        								
	        							case 7:
	        								statsDwellTime7.addValue(diffMinutes);
	        								
	        							case 8:
	        								statsDwellTime8.addValue(diffMinutes);
	        								
	        							case 9:
	        								statsDwellTime9.addValue(diffMinutes);
	        								
	        							case 10:
	        								statsDwellTime10.addValue(diffMinutes);
	        								
	        							case 11:
	        								statsDwellTime11.addValue(diffMinutes);
	        								
	        							case 12:
	        								statsDwellTime12.addValue(diffMinutes);
	        								
	        						}
		        					
	        					}
	        					
	        					//System.out.println("timing stop:" + previousStop + " start:" + dateStart + " @ stop:" + dateStop + " dwell:" + diffMinutes);
	        					
	        					dateStart = "";
	        					dateStop = "";
	        					
	        					previousStop = i;
	        					//System.out.println("-------");
	        				}
	        					
	        				
	        			}
	        			
	        			i++;
	        		}
	        	
	        		
	        	}
	        	
	        }
	        
	        System.out.println("Realworld Dwell time validation ....");
	        System.out.println("Mean | Stdev | Max | Min ");
	        System.out.println(statsDwellTime1.getMean() + " | " + statsDwellTime1.getStandardDeviation() + " | " + statsDwellTime1.getMax() + " | " + statsDwellTime1.getMin() );
	        System.out.println(statsDwellTime2.getMean() + " | " + statsDwellTime2.getStandardDeviation() + " | " + statsDwellTime2.getMax() + " | " + statsDwellTime2.getMin() );
	        System.out.println(statsDwellTime3.getMean() + " | " + statsDwellTime3.getStandardDeviation() + " | " + statsDwellTime3.getMax() + " | " + statsDwellTime3.getMin() );
	        System.out.println(statsDwellTime4.getMean() + " | " + statsDwellTime4.getStandardDeviation() + " | " + statsDwellTime4.getMax() + " | " + statsDwellTime4.getMin() );
	        System.out.println(statsDwellTime5.getMean() + " | " + statsDwellTime5.getStandardDeviation() + " | " + statsDwellTime5.getMax() + " | " + statsDwellTime5.getMin() );
	        System.out.println(statsDwellTime6.getMean() + " | " + statsDwellTime6.getStandardDeviation() + " | " + statsDwellTime6.getMax() + " | " + statsDwellTime6.getMin() );
	        System.out.println(statsDwellTime7.getMean() + " | " + statsDwellTime7.getStandardDeviation() + " | " + statsDwellTime7.getMax() + " | " + statsDwellTime7.getMin() );
	        System.out.println(statsDwellTime8.getMean() + " | " + statsDwellTime8.getStandardDeviation() + " | " + statsDwellTime8.getMax() + " | " + statsDwellTime8.getMin() );
	        System.out.println(statsDwellTime9.getMean() + " | " + statsDwellTime9.getStandardDeviation() + " | " + statsDwellTime9.getMax() + " | " + statsDwellTime9.getMin() );
	        System.out.println(statsDwellTime10.getMean() + " | " + statsDwellTime10.getStandardDeviation() + " | " + statsDwellTime10.getMax() + " | " + statsDwellTime10.getMin());
	        System.out.println(statsDwellTime11.getMean() + " | " + statsDwellTime11.getStandardDeviation() + " | " + statsDwellTime11.getMax() + " | " + statsDwellTime11.getMin());
	        System.out.println(statsDwellTime12.getMean() + " | " + statsDwellTime12.getStandardDeviation() + " | " + statsDwellTime12.getMax() + " | " + statsDwellTime12.getMin());
	        
	        System.out.println("..............................");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	
	
	
}
