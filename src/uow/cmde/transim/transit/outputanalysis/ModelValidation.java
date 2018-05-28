package uow.cmde.transim.transit.outputanalysis;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.commons.math.stat.inference.TestUtils; 

import com.csvreader.CsvWriter;

import uow.cmde.transim.transit.model.IStopActivities;
import uow.cmde.transim.transit.model.IStopActivity;
import uow.cmde.transim.transit.outputanalysis.MOOutputAnalyser.Fitness;
import uow.cmde.transim.util.AppConfig;
import uow.cmde.transim.util.TimeConverter;

public class ModelValidation {

	public static class Data
	{
			 public String stop; 
			 public double meanHeadway; 
			 public double stdevHeadway; 
			 public double minHeadway;
			 public double maxHeadway;
			 
			 public double meanDwellTime; 
			 public double stdevDwellTime;
			 public double minDwellTime;
			 public double maxDwellTime;
			 
			 public double meanPassengerOn;
			 public double stdevPassengerOn;
			 public double minPassengerOn;
			 public double maxPassengerOn;
			 
			 public double meanPassengerOff;
			 public double stdevPassengerOff;
			 public double minPassengerOff;
			 public double maxPassengerOff;
			 
			 public double meanPassengerLoad;
			 public double stdevPassengerLoad;
			 public double minPassengerLoad;
			 public double maxPassengerLoad;
			 
			 public double tstatisticHeadway;
	}
	 
	public static void validateHeadwayAdherence(Map<String, IStopActivities> map, String outputFile)
	{
		
		
		Object[] keys=map.keySet().toArray();
		ArrayList<Data> arrData = new ArrayList<Data>();
		
		
		for(Object o:keys)
		{
			SummaryStatistics statsHeadway = new SummaryStatistics();
			SummaryStatistics statsPassengerOn = new SummaryStatistics();
			SummaryStatistics statsPassengerOff = new SummaryStatistics();
			SummaryStatistics statsPassengerLoad = new SummaryStatistics();
			SummaryStatistics statsDwellTime = new SummaryStatistics();
			
			IStopActivities tmpActivities = map.get(o.toString());
			Data d = new Data();
			
			double totalHeadway = 0;
			int k = 0, l = 0, totalOn = 0, totalOff = 0;
			
			for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
			{
				
				String departureTime = stopActivity.getDepartureTime();
				String arrivalTime = stopActivity.getArrivalTime();
				String lastDepartureTime = stopActivity.getLastDepartureTime();
				
				int numberPassengerOn = stopActivity.getNumberPassengerOn();
				int numberPassengerOff = stopActivity.getNumberPassengerOff();
				int numberPassengerLoad = stopActivity.getNumberPassengerOnVehicle() + numberPassengerOn  ;
				
				if(lastDepartureTime!=null && !lastDepartureTime.equals(""))
				{
					//Calculate wait time
					double actualHeadwayInSecond = TimeConverter.convertTimeToSecond(departureTime) - TimeConverter.convertTimeToSecond(lastDepartureTime);
					statsHeadway.addValue(actualHeadwayInSecond/60);
					
					k++;
				}	
				
				double dwellTimeInSecond = TimeConverter.convertTimeToSecond(departureTime) - TimeConverter.convertTimeToSecond(arrivalTime);
				statsDwellTime.addValue(dwellTimeInSecond/60);
				statsPassengerOn.addValue(numberPassengerOn);
				statsPassengerOff.addValue(numberPassengerOff);
				statsPassengerLoad.addValue(numberPassengerLoad);
				
				l++;
			}
			
			
			if(k!=0)
			{
				
				d.meanHeadway = statsHeadway.getMean();
				d.stdevHeadway = statsHeadway.getStandardDeviation();
				d.minHeadway = statsHeadway.getMin();
				d.maxHeadway = statsHeadway.getMax();
				
			}
			
			
			d.stop = o.toString();
			d.meanPassengerOn = statsPassengerOn.getMean();
			d.stdevPassengerOn = statsPassengerOn.getStandardDeviation();
			d.minPassengerOn = statsPassengerOn.getMin();
			d.maxPassengerOn = statsPassengerOn.getMax();
			
			d.meanPassengerOff = statsPassengerOff.getMean();
			d.stdevPassengerOff = statsPassengerOff.getStandardDeviation();
			d.minPassengerOff = statsPassengerOff.getMin();
			d.maxPassengerOff = statsPassengerOff.getMax();
			
			d.meanPassengerLoad = statsPassengerLoad.getMean();
			d.stdevPassengerLoad = statsPassengerLoad.getStandardDeviation();
			d.minPassengerLoad = statsPassengerLoad.getMin();
			d.maxPassengerLoad = statsPassengerLoad.getMax();
			
			d.meanDwellTime = statsDwellTime.getMean();
			d.stdevDwellTime = statsDwellTime.getStandardDeviation();
			d.minDwellTime = statsDwellTime.getMin();
			d.maxDwellTime = statsDwellTime.getMax();
			
			arrData.add(d);
			
			try
			{
				
				d.tstatisticHeadway = TestUtils.t(15,statsHeadway);
			}
			catch(Exception ex)
			{
			  System.out.println(ex.getMessage());	
			}
			
		}
		
		saveFile(outputFile,arrData);
	}
	
	
	public static void validateTripTime(Map<String, IStopActivities> map, String outputFile)
	{
		
		
		Object[] keys=map.keySet().toArray();
		ArrayList<Data> arrData = new ArrayList<Data>();
		
		SummaryStatistics statsTripTime = new SummaryStatistics();
		
		
		for(Object o:keys)
		{
			
			IStopActivities tmpActivities = map.get(o.toString());
			//Data d = new Data();
			
		
			String startArrivalTime3 = "";
			String endArrivalTime3 = "";
			
			String startArrivalTime4 = "";
			String endArrivalTime4 = "";
			
			String startArrivalTime5 = "";
			String endArrivalTime5 = "";
			
			for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
			{
				
				if(stopActivity.getVehicleId().equals("vehicle3"))
				{
					
					if(stopActivity.getStop().getStopCode().equals("1"))
					{
						startArrivalTime3 = stopActivity.getArrivalTime();
					}
					
					if(stopActivity.getStop().getStopCode().equals("12"))
					{
						endArrivalTime3 = stopActivity.getArrivalTime();
					}
				}
				
				if(stopActivity.getVehicleId().equals("vehicle4"))
				{
					
					if(stopActivity.getStop().getStopCode().equals("1"))
					{
						startArrivalTime4 = stopActivity.getArrivalTime();
					}
					
					if(stopActivity.getStop().getStopCode().equals("12"))
					{
						endArrivalTime4 = stopActivity.getArrivalTime();
					}
				}
				
				if(stopActivity.getVehicleId().equals("vehicle4"))
				{
					
					if(stopActivity.getStop().getStopCode().equals("1"))
					{
						startArrivalTime5 = stopActivity.getArrivalTime();
					}
					
					if(stopActivity.getStop().getStopCode().equals("12"))
					{
						endArrivalTime5 = stopActivity.getArrivalTime();
					}
				}
				
			}
			
			
			if(!startArrivalTime3.equals(""))
			{
				double tripTimeInSecond = TimeConverter.convertTimeToSecond(endArrivalTime3) - TimeConverter.convertTimeToSecond(startArrivalTime3);
				
				statsTripTime.addValue((tripTimeInSecond/60 - 10));
			
			}
			
			if(!startArrivalTime4.equals(""))
			{
				double tripTimeInSecond = TimeConverter.convertTimeToSecond(endArrivalTime4) - TimeConverter.convertTimeToSecond(startArrivalTime4);
				
				statsTripTime.addValue((tripTimeInSecond/60 - 10));
			}
			
			if(!startArrivalTime5.equals(""))
			{
				double tripTimeInSecond = TimeConverter.convertTimeToSecond(endArrivalTime5) - TimeConverter.convertTimeToSecond(startArrivalTime5);
				
				statsTripTime.addValue((tripTimeInSecond/60 - 10));
			}
			
			
		}
		
		System.out.println(" Trip time validation ....");
		System.out.println(" Mean | stdev | min | max");
		System.out.println(statsTripTime.getMean() + " | " + statsTripTime.getStandardDeviation() + " | " + statsTripTime.getMin() + " | " + statsTripTime.getMax());
		System.out.println(" .........................");
		//saveFile(outputFile,arrData);
	}
	
	
	public static void validateScheduleAdherence(Map<String, IStopActivities> map, String outputFile)
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
		
		Object[] keys=map.keySet().toArray();
		ArrayList<Data> arrData = new ArrayList<Data>();
		
		SummaryStatistics statsSchedule = new SummaryStatistics();
		
		for(Object o:keys)
		{
			
			IStopActivities tmpActivities = map.get(o.toString());
	
			int l = 0;
			
			for(IStopActivity stopActivity:tmpActivities.getAllStopActivities())
			{
				
				
				String arrivalTime = stopActivity.getArrivalTime();
				
				if(arrivalTime!=null && !arrivalTime.equals(""))
				{
					switch(Integer.parseInt(stopActivity.getStop().getStopCode()))
					{
						case 1:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime1[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 2:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime2[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 3:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime3[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 4:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime4[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 5:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime5[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 6:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime6[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 7:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime7[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 8:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime8[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 9:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime9[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 10:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime10[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 11:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime11[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						case 12:
							for(int i=1;i<=17;i++)
							{
								double minutes= Math.abs((TimeConverter.convertTimeToSecond(arrivalTime12[i]) - TimeConverter.convertTimeToSecond(arrivalTime))/60.0);
								if(minutes <5 && minutes >0 )
								{
									statsSchedule.addValue(minutes);
								}
							}
							break;
						
					}
					//Calculate wait time
					
					
					//System.out.println(stopActivity.getStop().getStopCode() + " @ " + arrivalTime);
				}	
				
				
				l++;
			}
			
			
			
			
		}
		
		  	System.out.println("Model Schedule validation ....");
	        System.out.println("Mean | Stdev | Max | Min  | Obs");
	        System.out.println(statsSchedule.getMean() + " | " + statsSchedule.getStandardDeviation() + " | " + statsSchedule.getMax() + " | " + statsSchedule.getMin() + " | " + statsSchedule.getN() );
	      
	        
	        System.out.println("..............................");
		
		//saveFile(outputFile,arrData);
	}
	
	
	
	public static void saveFile(String outputFile, ArrayList<Data> arrData)
	{
		try
		{
			boolean alreadyExists = new File(outputFile).exists();
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
			
			
			if (!alreadyExists)
			{
				csvOutput.write("stop");
				csvOutput.write("mean headway");
				csvOutput.write("stdev headway");
				csvOutput.write("min headway");
				csvOutput.write("max headway");
				
				csvOutput.write("mean dwelltime");
				csvOutput.write("stdev dwelltime");
				csvOutput.write("min dwelltime");
				csvOutput.write("max dwelltime");
				
				csvOutput.write("mean on");
				csvOutput.write("stdev on");
				csvOutput.write("min on");
				csvOutput.write("max on");
				
				csvOutput.write("mean off");
				csvOutput.write("stdev off");
				csvOutput.write("min off");
				csvOutput.write("max off");
				
				csvOutput.write("mean passenger load");
				csvOutput.write("stdev passenger load");
				csvOutput.write("min passenger load");
				csvOutput.write("max passenger load");
				
				csvOutput.write("t statistic headway");
				
				csvOutput.endRecord();
				
			}
		
			for(Data d:arrData)
			{
				csvOutput.write(d.stop);
				csvOutput.write("" + d.meanHeadway);
				csvOutput.write("" + d.stdevHeadway);
				csvOutput.write("" + d.minHeadway);
				csvOutput.write("" + d.maxHeadway);
				
				csvOutput.write("" + d.meanDwellTime);
				csvOutput.write("" + d.stdevDwellTime);
				csvOutput.write("" + d.minDwellTime);
				csvOutput.write("" + d.maxDwellTime);
				
				csvOutput.write("" + d.meanPassengerOn);
				csvOutput.write("" + d.stdevPassengerOn);
				csvOutput.write("" + d.minPassengerOn);
				csvOutput.write("" + d.maxPassengerOn);
				
				csvOutput.write("" + d.meanPassengerOff);
				csvOutput.write("" + d.stdevPassengerOff);
				csvOutput.write("" + d.minPassengerOff);
				csvOutput.write("" + d.maxPassengerOff);
				
				csvOutput.write("" + d.meanPassengerLoad);
				csvOutput.write("" + d.stdevPassengerLoad);
				csvOutput.write("" + d.minPassengerLoad);
				csvOutput.write("" + d.maxPassengerLoad);
				
				csvOutput.write("" + d.tstatisticHeadway);
				
				csvOutput.endRecord();
			}
			
			
			
			
			csvOutput.close();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
